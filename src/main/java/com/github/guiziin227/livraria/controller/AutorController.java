package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.AutorRequestDTO;
import com.github.guiziin227.livraria.dto.AutorResponseDTO;
import com.github.guiziin227.livraria.mapper.AutorMapper;
import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.repositories.AutorRepository;
import com.github.guiziin227.livraria.services.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> createAutor(@Valid @RequestBody AutorRequestDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        Autor createdAutor = autorService.createAutor(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorMapper.toResponseDTO(createdAutor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> findById(@Valid @PathVariable Long id) {
        Autor autor = autorService.findById(id);
        return ResponseEntity.ok(autorMapper.toResponseDTO(autor));
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> findAll() {
        List<Autor> autores = autorService.findAll();
        return ResponseEntity.ok(autorMapper.toResponseDTOList(autores));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> updateAutor(@PathVariable Long id, @Valid @RequestBody AutorRequestDTO dto) {
        Autor updatedAutor = autorService.updateAutor(id, dto);
        return ResponseEntity.ok(autorMapper.toResponseDTO(updatedAutor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
