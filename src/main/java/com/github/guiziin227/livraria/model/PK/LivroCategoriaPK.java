package com.github.guiziin227.livraria.model.PK;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Embeddable
public class LivroCategoriaPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_livro", nullable = false)
    private Long idLivro;

    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LivroCategoriaPK that = (LivroCategoriaPK) o;
        return Objects.equals(idLivro, that.idLivro) && Objects.equals(idCategoria, that.idCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLivro, idCategoria);
    }
}
