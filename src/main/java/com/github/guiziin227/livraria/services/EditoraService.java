package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
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

    @Transactional
    public Editora createEditora(Editora editora) {
        logger.info("Criando uma Editora");
        return editoraRepositoy.save(editora);
    }

    @Transactional(readOnly = true)
    public Editora getEditoraById(Long id) {
        logger.info("Buscando Editora com ID: {}", id);
        var entity = editoraRepositoy.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Editora não encontrada com ID: " + id)
        );
        return entity;
    }

    @Transactional(readOnly = true)
    public List<Editora> getAllEditoras() {
        logger.info("Buscando todas as Editoras");
        return editoraRepositoy.findAll();
    }

    @Transactional
    public Editora updateEditora(Long id,Editora editora) {
        logger.info("Atualizando uma Editora");
        Editora editoraAtual = editoraRepositoy.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Editora não encontrada com ID: " + editora.getId())
                );

        editoraAtual.setName(editora.getName());
        editoraAtual.setCnpj(editora.getCnpj());
        editoraAtual.setCity(editora.getCity());
        editoraAtual.setCountry(editora.getCountry());

        return editoraRepositoy.save(editoraAtual);
    }

    @Transactional
    public void deleteEditora(Long id) {
        logger.info("Deletando Editora com ID: {}", id);
        var entity = editoraRepositoy.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Editora não encontrada com ID: " + id)
        );
        editoraRepositoy.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<Livro> getLivrosByEditoraId(Long editoraId) {
        logger.info("Buscando livros da editora com ID: {}", editoraId);

        if (getEditoraById(editoraId) == null) {
            throw new ResourceNotFoundException("Editora não encontrada com ID: " + editoraId);
        }

        return livroRepository.findByPublisherId(editoraId);
    }

}
