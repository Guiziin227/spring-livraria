package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.services.EditoraService;
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

    @PutMapping
    public Editora updateEditora(@RequestBody Editora editora) {
        return editoraService.updateEditora(editora);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEditora(@PathVariable Long id) {
        editoraService.deleteEditora(id);
    }
}
