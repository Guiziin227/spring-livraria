package com.github.guiziin227.livraria.model.JOIN;


import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.model.PK.LivroAutorPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_livro_autor")
public class LivroAutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LivroAutorPK id;

    @ManyToOne
    @MapsId("idLivro")
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne
    @MapsId("idAutor")
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LivroAutor that = (LivroAutor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
