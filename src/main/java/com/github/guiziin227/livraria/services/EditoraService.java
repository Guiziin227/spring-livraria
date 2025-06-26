package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.repositories.EditoraRepositoy;
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

    @Transactional
    public Editora createEditora(Editora editora) {
        logger.info("Criando uma Editora");
        return editoraRepositoy.save(editora);
    }

    @Transactional(readOnly = true)
    public Editora getEditoraById(Long id) {
        logger.info("Buscando Editora com ID: {}", id);
        return editoraRepositoy.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Editora> getAllEditoras() {
        logger.info("Buscando todas as Editoras");
        return editoraRepositoy.findAll();
    }

    @Transactional
    public Editora updateEditora(Editora editora) {
        logger.info("Atualizando uma Editora");
        Editora editoraAtual = editoraRepositoy.findById(editora.getId())
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
        if (editoraRepositoy.existsById(id)) {
            editoraRepositoy.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Editora não encontrada com ID: " + id);
        }
    }

}
