package com.github.guiziin227.livraria.model.JOIN;

import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
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
@Table(name = "tb_livro_categoria")
public class LivroCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LivroCategoriaPK id;

    @ManyToOne
    @MapsId("idLivro")
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne
    @MapsId("idCategoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LivroCategoria that = (LivroCategoria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
