package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.responses.LivroResponseDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.exceptions.DuplicateResourceException;
import com.github.guiziin227.livraria.exceptions.BusinessRuleException;
import com.github.guiziin227.livraria.exceptions.RelationshipException;
import com.github.guiziin227.livraria.mapper.LivroMapper;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.repositories.EditoraRepositoy;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LivroService {

    Logger logger = LoggerFactory.getLogger(LivroService.class);

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EditoraRepositoy editoraRepository;

    @Autowired
    private LivroMapper livroMapper;

    @Transactional
    public LivroResponseDTO createLivro(LivroRequestDTO livroDTO) {
        logger.info("Criando um Livro: {}", livroDTO.titulo());

        // Verificar se já existe livro com o mesmo título e editora
        if (livroRepository.existsByTituloAndPublisherId(livroDTO.titulo(),
                livroDTO.publisher() != null ? livroDTO.publisher().id() : null)) {
            throw new DuplicateResourceException("Já existe um livro com o título '" + livroDTO.titulo() +
                    "' da mesma editora");
        }

        // Verificar se a editora existe
        if (livroDTO.publisher() != null && livroDTO.publisher().id() != null) {
            if (!editoraRepository.existsById(livroDTO.publisher().id())) {
                throw new RelationshipException("Editora não encontrada com ID: " + livroDTO.publisher().id());
            }
        }

        Livro livro = livroMapper.toEntity(livroDTO);

        // Buscar a editora completa
        if (livro.getPublisher() != null && livro.getPublisher().getId() != null) {
            Editora editora = editoraRepository.findById(livro.getPublisher().getId())
                    .orElseThrow(() -> new RelationshipException("Editora não encontrada com ID: " + livro.getPublisher().getId()));
            livro.setPublisher(editora);
        }

        try {
            Livro savedLivro = livroRepository.save(livro);
            logger.info("Livro criado com sucesso: {}", savedLivro.getTitulo());
            return livroMapper.toResponseDTO(savedLivro);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Erro ao salvar livro: dados duplicados detectados");
        }
    }

    @Transactional(readOnly = true)
    public LivroResponseDTO findById(Long id) {
        logger.info("Buscando livro com ID: {}", id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do livro deve ser um número positivo");
        }

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        return livroMapper.toResponseDTO(livro);
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDTO> findAll() {
        logger.info("Buscando todos os livros");
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            logger.warn("Nenhum livro encontrado");
            throw new ResourceNotFoundException("Nenhum livro encontrado");
        }

        return livroMapper.toResponseDTOList(livros);
    }

    @Transactional
    public LivroResponseDTO updateLivro(Long id, LivroRequestDTO livroDTO) {
        logger.info("Atualizando livro com ID: {}", id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do livro deve ser um número positivo");
        }

        Livro existingLivro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        // Verificar se está tentando alterar para um título que já existe em outra editora
        if (!existingLivro.getTitulo().equals(livroDTO.titulo()) &&
                livroRepository.existsByTituloAndPublisherId(livroDTO.titulo(),
                        livroDTO.publisher() != null ? livroDTO.publisher().id() : null)) {
            throw new DuplicateResourceException("Já existe um livro com o título '" + livroDTO.titulo() +
                    "' da mesma editora");
        }

        // Verificar se a editora existe (se foi informada)
        if (livroDTO.publisher() != null && livroDTO.publisher().id() != null) {
            if (!editoraRepository.existsById(livroDTO.publisher().id())) {
                throw new RelationshipException("Editora não encontrada com ID: " + livroDTO.publisher().id());
            }
        }

        livroMapper.updateEntityFromDTO(livroDTO, existingLivro);

        try {
            Livro updatedLivro = livroRepository.save(existingLivro);
            logger.info("Livro atualizado com sucesso: {}", updatedLivro.getTitulo());
            return livroMapper.toResponseDTO(updatedLivro);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Erro ao atualizar livro: dados duplicados detectados");
        }
    }

    @Transactional
    public void deleteLivro(Long id) {
        logger.info("Deletando livro com ID: {}", id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do livro deve ser um número positivo");
        }

        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + id);
        }

        try {
            livroRepository.deleteById(id);
            logger.info("Livro deletado com sucesso");
        } catch (DataIntegrityViolationException e) {
            throw new BusinessRuleException("Não é possível deletar este livro pois ele possui vendas ou relacionamentos associados");
        }
    }
}
