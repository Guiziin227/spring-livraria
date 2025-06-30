package com.github.guiziin227.livraria.model;

import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_livro")
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String resumo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(nullable = false)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(nullable = false, name = "editora_id")
    private Editora publisher;

    @OneToMany(mappedBy = "livro")
    private Set<LivroCategoria> livroCategories = new HashSet<>();

    @OneToMany(mappedBy = "livro")
    private Set<LivroAutor> livroAutores = new HashSet<>();

    // Método utilitário para obter categorias (usado pelos mappers/DTOs)
    public Set<Categoria> getCategorias() {
        return livroCategories.stream()
                .map(LivroCategoria::getCategoria)
                .collect(java.util.stream.Collectors.toSet());
    }

    // Método utilitário para obter autores (usado pelos mappers/DTOs)
    public Set<Autor> getAutores() {
        return livroAutores.stream()
                .map(LivroAutor::getAutor)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
