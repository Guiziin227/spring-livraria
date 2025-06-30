package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.AutorRequestDTO;
import com.github.guiziin227.livraria.mapper.AutorMapper;
import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.repositories.AutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AutorService {

    Logger logger = LoggerFactory.getLogger(AutorService.class);

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorMapper autorMapper;

    public Autor createAutor(Autor autor) {
        logger.info("Criando novo autor: {}", autor.getName());
        return autorRepository.save(autor);
    }

    public Autor findById(Long id) {
        logger.info("Buscando autor com ID: {}", id);
        return autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));
    }

    public List<Autor> findAll() {
        logger.info("Buscando todos os autores");
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            logger.warn("Nenhum autor encontrado.");
            throw new RuntimeException("Nenhum autor encontrado.");
        }
        return autores;
    }

    public Autor updateAutor(Long id, AutorRequestDTO dto) {
        logger.info("Atualizando autor com ID: {}", id);

        Autor existingAutor = findById(id);
        autorMapper.updateEntityFromDTO(dto, existingAutor);
        logger.info("Autor atualizado: {}", existingAutor.getName());
        return autorRepository.save(existingAutor);
    }

    public void deleteById(Long id) {
        logger.info("Deletando autor com ID: {}", id);
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado com ID: " + id);
        }
        autorRepository.deleteById(id);
        logger.info("Autor deletado com sucesso.");
    }
}
