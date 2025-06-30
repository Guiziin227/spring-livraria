package com.github.guiziin227.livraria.model;

import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.PK.LivroAutorPK;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_autor")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 70)
    private String nacionality;

    @Column(nullable = false, length = 1000)
    private String biography;

    @OneToMany(mappedBy = "autor")
    private Set<LivroAutor> livroAutors = new HashSet<>();

    // Métodos utilitários para gerenciar categorias
    public Set<Livro> getLivros() {
        return livroAutors.stream()
                .map(LivroAutor::getLivro)
                .collect(java.util.stream.Collectors.toSet());
    }

    public void addLivros(Livro livro) {
        LivroAutorPK pk = new LivroAutorPK(livro.getId(),this.id);
        LivroAutor livroAutor = new LivroAutor(pk, livro,this);
        this.livroAutors.add(livroAutor);
        livro.getLivroAutores().add(livroAutor);
    }

//    public void removeCategoria(Categoria categoria) {
//        LivroCategoria livroCategoria = livroCategories.stream()
//                .filter(lc -> lc.getCategoria().equals(categoria))
//                .findFirst()
//                .orElse(null);
//        if (livroCategoria != null) {
//            this.livroCategories.remove(livroCategoria);
//            categoria.getLivroCategories().remove(livroCategoria);
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(id, autor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
