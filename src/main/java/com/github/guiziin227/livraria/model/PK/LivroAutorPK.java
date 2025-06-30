package com.github.guiziin227.livraria.model.PK;

import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.model.Livro;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class LivroAutorPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id_livro", nullable = false)
    private Long idLivro;

    @Column(name = "id_autor", nullable = false)
    private Long idAutor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LivroAutorPK that = (LivroAutorPK) o;
        return Objects.equals(idLivro, that.idLivro) && Objects.equals(idAutor, that.idAutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLivro, idAutor);
    }
}
