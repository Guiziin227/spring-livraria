package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.repositories.CategoriaRepository;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.dto.CategoriaRequestDTO;
import com.github.guiziin227.livraria.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<Categoria> findAll() {
        logger.info("Buscando todas as categorias");
        if (categoriaRepository.findAll().isEmpty()) {
            logger.warn("Nenhuma categoria encontrada.");
            throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
        }
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        logger.info("Buscando categoria com ID: {}", id);
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        logger.info("Salvando nova categoria: {}", categoria.getName());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, CategoriaRequestDTO dto) {
        logger.info("Atualizando categoria com ID: {}", id);
        Categoria existingCategoria = findById(id);
        categoriaMapper.updateEntityFromDTO(dto, existingCategoria);
        return categoriaRepository.save(existingCategoria);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deletando categoria com ID: {}", id);
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
