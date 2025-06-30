package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.responses.LivroResponseDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.mapper.LivroMapper;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.repositories.EditoraRepositoy;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // Verificar se a editora existe
        if (livroDTO.publisher() != null && livroDTO.publisher().id() != null) {
            if (!editoraRepository.existsById(livroDTO.publisher().id())) {
                throw new ResourceNotFoundException("Editora não encontrada com ID: " + livroDTO.publisher().id());
            }
        }

        Livro livro = livroMapper.toEntity(livroDTO);

        // Buscar a editora completa
        if (livro.getPublisher() != null && livro.getPublisher().getId() != null) {
            Editora editora = editoraRepository.findById(livro.getPublisher().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com ID: " + livro.getPublisher().getId()));
            livro.setPublisher(editora);
        }

        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toResponseDTO(livroSalvo);
    }

    @Transactional(readOnly = true)
    public LivroResponseDTO getLivroById(Long id) {
        logger.info("Buscando Livro com ID: {}", id);
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        // Carregar a editora completa
        if (livro.getPublisher() != null) {
            Editora editora = editoraRepository.findById(livro.getPublisher().getId()).orElse(null);
            livro.setPublisher(editora);
        }

        return livroMapper.toResponseDTO(livro);
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDTO> getAllLivros() {
        logger.info("Buscando todos os Livros");
        List<Livro> livros = livroRepository.findAll();

        // Carregar a editora completa para cada livro
        for (Livro livro : livros) {
            if (livro.getPublisher() != null) {
                Editora editora = editoraRepository.findById(livro.getPublisher().getId()).orElse(null);
                livro.setPublisher(editora);
            }
        }

        return livroMapper.toResponseDTOList(livros);
    }

    @Transactional
    public LivroResponseDTO updateLivro(Long id, LivroRequestDTO livroDTO) {
        logger.info("Atualizando Livro com ID: {}", id);

        Livro livroAtual = livroRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        // Atualizar campos básicos
        livroMapper.updateEntityFromDTO(livroDTO, livroAtual);

        // Atualizar editora se fornecida
        if (livroDTO.publisher() != null && livroDTO.publisher().id() != null) {
            Editora editora = editoraRepository.findById(livroDTO.publisher().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com ID: " + livroDTO.publisher().id()));
            livroAtual.setPublisher(editora);
        }

        Livro livroAtualizado = livroRepository.save(livroAtual);
        return livroMapper.toResponseDTO(livroAtualizado);
    }

    @Transactional
    public void deleteLivro(Long id) {
        logger.info("Deletando Livro com ID: {}", id);

        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }
}
