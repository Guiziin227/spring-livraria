package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.EditoraRequestDTO;
import com.github.guiziin227.livraria.dto.EditoraResponseDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.mapper.EditoraMapper;
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
public class EditoraService {

    Logger logger = LoggerFactory.getLogger(EditoraService.class);

    @Autowired
    private EditoraRepositoy editoraRepositoy;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EditoraMapper editoraMapper;

    @Autowired
    private LivroMapper livroMapper;

    @Transactional
    public EditoraResponseDTO createEditora(EditoraRequestDTO editoraDTO) {
        logger.info("Criando uma Editora");
        Editora editora = editoraMapper.toEntity(editoraDTO);
        Editora editoraSalva = editoraRepositoy.save(editora);
        return editoraMapper.toResponseDTO(editoraSalva);
    }

    @Transactional(readOnly = true)
    public EditoraResponseDTO getEditoraById(Long id) {
        logger.info("Buscando Editora com ID: {}", id);
        Editora editora = editoraRepositoy.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com ID: " + id));
        return editoraMapper.toResponseDTO(editora);
    }

    @Transactional(readOnly = true)
    public List<EditoraResponseDTO> getAllEditoras() {
        logger.info("Buscando todas as Editoras");
        List<Editora> editoras = editoraRepositoy.findAll();
        return editoraMapper.toResponseDTOList(editoras);
    }

    @Transactional
    public EditoraResponseDTO updateEditora(Long id, EditoraRequestDTO editoraDTO) {
        logger.info("Atualizando Editora com ID: {}", id);
        Editora editoraAtual = editoraRepositoy.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com ID: " + id));

        editoraMapper.updateEntityFromDTO(editoraDTO, editoraAtual);
        Editora editoraAtualizada = editoraRepositoy.save(editoraAtual);
        return editoraMapper.toResponseDTO(editoraAtualizada);
    }

    @Transactional
    public void deleteEditora(Long id) {
        logger.info("Deletando Editora com ID: {}", id);
        if (!editoraRepositoy.existsById(id)) {
            throw new ResourceNotFoundException("Editora não encontrada com ID: " + id);
        }
        editoraRepositoy.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDTO> getLivrosByEditoraId(Long editoraId) {
        logger.info("Buscando livros da editora com ID: {}", editoraId);

        if (!editoraRepositoy.existsById(editoraId)) {
            throw new ResourceNotFoundException("Editora não encontrada com ID: " + editoraId);
        }

        List<Livro> livros = livroRepository.findByPublisherId(editoraId);

        // Carregar editora completa para cada livro
        for (Livro livro : livros) {
            if (livro.getPublisher() != null) {
                Editora editora = editoraRepositoy.findById(livro.getPublisher().getId()).orElse(null);
                livro.setPublisher(editora);
            }
        }

        return livroMapper.toResponseDTOList(livros);
    }
}
