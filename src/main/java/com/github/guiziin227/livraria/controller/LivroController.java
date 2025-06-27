package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> createLivro(@Valid @RequestBody LivroRequestDTO livroDTO) {
        LivroResponseDTO livro = livroService.createLivro(livroDTO);
        return ResponseEntity.status(201).body(livro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable Long id) {
        LivroResponseDTO livro = livroService.getLivroById(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        List<LivroResponseDTO> livros = livroService.getAllLivros();
        return ResponseEntity.ok(livros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> updateLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO livroDTO) {
        LivroResponseDTO updatedLivro = livroService.updateLivro(id, livroDTO);
        return ResponseEntity.ok(updatedLivro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }
}
