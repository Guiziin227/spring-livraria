package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.EditoraRequestDTO;
import com.github.guiziin227.livraria.dto.EditoraResponseDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.services.EditoraService;
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
@RequestMapping("api/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @PostMapping
    public ResponseEntity<EditoraResponseDTO> createEditora(@Valid @RequestBody EditoraRequestDTO editoraDTO) {
        EditoraResponseDTO createdEditora = editoraService.createEditora(editoraDTO);
        return ResponseEntity.status(201).body(createdEditora);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> getEditoraById(@PathVariable Long id) {
        EditoraResponseDTO editora = editoraService.getEditoraById(id);
        return ResponseEntity.ok(editora);
    }

    @GetMapping
    public ResponseEntity<List<EditoraResponseDTO>> getAllEditoras() {
        List<EditoraResponseDTO> editoras = editoraService.getAllEditoras();
        return ResponseEntity.ok(editoras);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> updateEditora(@PathVariable Long id, @Valid @RequestBody EditoraRequestDTO editoraDTO) {
        EditoraResponseDTO updatedEditora = editoraService.updateEditora(id, editoraDTO);
        return ResponseEntity.ok(updatedEditora);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditora(@PathVariable Long id) {
        editoraService.deleteEditora(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/livros")
    public ResponseEntity<List<LivroResponseDTO>> getLivrosByEditora(@PathVariable Long id) {
        List<LivroResponseDTO> livros = editoraService.getLivrosByEditoraId(id);
        return ResponseEntity.ok(livros);
    }
}
