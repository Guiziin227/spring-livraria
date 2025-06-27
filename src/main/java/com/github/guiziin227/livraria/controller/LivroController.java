package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LivroResponseDTO createLivro(@Valid @RequestBody LivroRequestDTO livroDTO) {
        return livroService.createLivro(livroDTO);
    }

    @GetMapping("/{id}")
    public LivroResponseDTO getLivroById(@PathVariable Long id) {
        return livroService.getLivroById(id);
    }

    @GetMapping
    public List<LivroResponseDTO> getAllLivros() {
        return livroService.getAllLivros();
    }

    @PutMapping("/{id}")
    public LivroResponseDTO updateLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO livroDTO) {
        return livroService.updateLivro(id, livroDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
    }
}
