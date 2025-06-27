package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.EditoraRequestDTO;
import com.github.guiziin227.livraria.dto.EditoraResponseDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.services.EditoraService;
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
@RequestMapping("api/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @PostMapping
    public EditoraResponseDTO createEditora(@Valid @RequestBody EditoraRequestDTO editoraDTO) {
        return editoraService.createEditora(editoraDTO);
    }

    @GetMapping("/{id}")
    public EditoraResponseDTO getEditoraById(@PathVariable Long id) {
        return editoraService.getEditoraById(id);
    }

    @GetMapping
    public List<EditoraResponseDTO> getAllEditoras() {
        return editoraService.getAllEditoras();
    }

    @PutMapping("/{id}")
    public EditoraResponseDTO updateEditora(@PathVariable Long id, @Valid @RequestBody EditoraRequestDTO editoraDTO) {
        return editoraService.updateEditora(id, editoraDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEditora(@PathVariable Long id) {
        editoraService.deleteEditora(id);
    }

    @GetMapping("/{id}/livros")
    public List<LivroResponseDTO> getLivrosByEditora(@PathVariable Long id) {
        return editoraService.getLivrosByEditoraId(id);
    }
}
