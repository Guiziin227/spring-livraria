package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.repositories.CategoriaRepository;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.dto.CategoriaRequestDTO;
import com.github.guiziin227.livraria.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, CategoriaRequestDTO dto) {
        Categoria existingCategoria = findById(id);
        categoriaMapper.updateEntityFromDTO(dto, existingCategoria);
        return categoriaRepository.save(existingCategoria);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
