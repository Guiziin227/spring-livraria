package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.AutorResponseDTO;
import com.github.guiziin227.livraria.dto.LivroAutorRequestDTO;
import com.github.guiziin227.livraria.dto.LivroAutorResponseDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.mapper.AutorMapper;
import com.github.guiziin227.livraria.mapper.LivroAutorMapper;
import com.github.guiziin227.livraria.mapper.LivroMapper;
import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.services.LivroAutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livro-autor")
public class LivroAutorController {

    @Autowired
    private LivroAutorService livroAutorService;

    @Autowired
    private LivroAutorMapper livroAutorMapper;

    @Autowired
    private LivroMapper livroMapper;

    @Autowired
    private AutorMapper autorMapper;

    @PostMapping("/associar")
    public ResponseEntity<LivroAutorResponseDTO> associarLivroAutor(@Valid @RequestBody LivroAutorRequestDTO dto) {

        LivroAutor livroAutor = livroAutorService.associarLivroAutor(dto.livroId(), dto.autorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(livroAutorMapper.toResponseDTO(livroAutor));
    }

    @DeleteMapping("/desassociar")
    public ResponseEntity<Void> desassociarLivroAutor(
            @Valid @RequestBody LivroAutorRequestDTO dto) {

        livroAutorService.desassociarLivroAutor(dto.livroId(), dto.autorId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/livro/{livroId}/autores")
    public ResponseEntity<List<AutorResponseDTO>> buscarAutoresPorLivro(@PathVariable Long livroId) {
        List<Autor> autores = livroAutorService.buscarAutoresPorLivro(livroId);
        List<AutorResponseDTO> autoresDTO = autores.stream()
                .map(autorMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoresDTO);
    }

    @GetMapping("/autor/{autorId}/livros")
    public ResponseEntity<List<LivroResponseDTO>> buscarLivrosPorAutor(@PathVariable Long autorId) {
        List<Livro> livros = livroAutorService.buscarLivrosPorAutor(autorId);
        List<LivroResponseDTO> livrosDTO = livros.stream()
                .map(livroMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livrosDTO);
    }

    @GetMapping("/verificar-associacao")
    public ResponseEntity<Boolean> verificarAssociacao(
            @RequestParam Long livroId,
            @RequestParam Long autorId) {
        boolean existe = livroAutorService.existeAssociacao(livroId, autorId);
        return ResponseEntity.ok(existe);
    }
}
