package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.services.EditoraService;
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
    public Editora createEditora(@RequestBody Editora editora) {
        return editoraService.createEditora(editora);
    }

    @GetMapping("/{id}")
    public Editora getEditoraById(@PathVariable Long id) {
        return editoraService.getEditoraById(id);
    }

    @GetMapping
    public List<Editora> getAllEditoras() {
        return editoraService.getAllEditoras();
    }

    @PutMapping("/{id}")
    public Editora updateEditora(@PathVariable Long id, @RequestBody Editora editora) {
        return editoraService.updateEditora(id, editora);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditora(@PathVariable Long id) {
        editoraService.deleteEditora(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/livros")
    public List<Livro> getLivrosByEditora(@PathVariable Long id) {
        return editoraService.getLivrosByEditoraId(id);
    }
}
