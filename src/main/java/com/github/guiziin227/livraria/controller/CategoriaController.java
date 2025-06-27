package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.CategoriaRequestDTO;
import com.github.guiziin227.livraria.dto.CategoriaResponseDTO;
import com.github.guiziin227.livraria.mapper.CategoriaMapper;
import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaResponseDTO> response = categoriaMapper.toResponseDTOList(categorias);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaMapper.toResponseDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria savedCategoria = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaMapper.toResponseDTO(savedCategoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id,
                                                      @Valid @RequestBody CategoriaRequestDTO dto) {
        Categoria updatedCategoria = categoriaService.update(id, dto);
        return ResponseEntity.ok(categoriaMapper.toResponseDTO(updatedCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
