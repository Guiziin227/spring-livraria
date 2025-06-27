package com.github.guiziin227.livraria.model;

import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "categoria")
    private Set<LivroCategoria> livroCategories = new HashSet<>();

    // Métodos utilitários para gerenciar livros
    public Set<Livro> getLivros() {
        return livroCategories.stream()
                .map(LivroCategoria::getLivro)
                .collect(java.util.stream.Collectors.toSet());
    }

    public void addLivro(Livro livro) {
        LivroCategoriaPK pk = new LivroCategoriaPK(livro.getId(), this.id);
        LivroCategoria livroCategoria = new LivroCategoria(pk, livro, this);
        this.livroCategories.add(livroCategoria);
        livro.getLivroCategories().add(livroCategoria);
    }

    public void removeLivro(Livro livro) {
        LivroCategoria livroCategoria = livroCategories.stream()
                .filter(lc -> lc.getLivro().equals(livro))
                .findFirst()
                .orElse(null);
        if (livroCategoria != null) {
            this.livroCategories.remove(livroCategoria);
            livro.getLivroCategories().remove(livroCategoria);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id != null && id.equals(categoria.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
