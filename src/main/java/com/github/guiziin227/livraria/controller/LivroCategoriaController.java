package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.CategoriaResponseDTO;
import com.github.guiziin227.livraria.dto.LivroCategoriaRequestDTO;
import com.github.guiziin227.livraria.dto.LivroCategoriaResponseDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.mapper.CategoriaMapper;
import com.github.guiziin227.livraria.mapper.LivroCategoriaMapper;
import com.github.guiziin227.livraria.mapper.LivroMapper;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.services.LivroCategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livro-categoria")
public class LivroCategoriaController {

    @Autowired
    private LivroCategoriaService livroCategoriaService;

    @Autowired
    private LivroCategoriaMapper livroCategoriaMapper;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private LivroMapper livroMapper;

    @PostMapping("/associar")
    public ResponseEntity<LivroCategoriaResponseDTO> associarLivroCategoria(
            @Valid @RequestBody LivroCategoriaRequestDTO dto) {
        LivroCategoria resultado = livroCategoriaService.associarLivroCategoria(dto.livroId(), dto.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroCategoriaMapper.toResponseDTO(resultado));
    }

    @DeleteMapping("/desassociar")
    public ResponseEntity<Void> desassociarLivroCategoria(
            @Valid @RequestBody LivroCategoriaRequestDTO dto) {
        livroCategoriaService.desassociarLivroCategoria(dto.livroId(), dto.categoriaId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/livro/{livroId}/categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarCategoriasPorLivro(@PathVariable Long livroId) {
        List<CategoriaResponseDTO> categorias = livroCategoriaService.buscarCategoriasPorLivro(livroId)
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/categoria/{categoriaId}/livros")
    public ResponseEntity<List<LivroResponseDTO>> buscarLivrosPorCategoria(@PathVariable Long categoriaId) {
        List<LivroResponseDTO> livros = livroCategoriaService.buscarLivrosPorCategoria(categoriaId)
                .stream()
                .map(livroMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> verificarAssociacao(
            @RequestParam Long livroId,
            @RequestParam Long categoriaId) {
        boolean existe = livroCategoriaService.existeAssociacao(livroId, categoriaId);
        return ResponseEntity.ok(existe);
    }
}
