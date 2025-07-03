package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByPublisherId(Long publisherId);

    boolean existsByTitulo(String titulo);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Livro l WHERE l.titulo = :titulo AND l.publisher.id = :publisherId")
    boolean existsByTituloAndPublisherId(@Param("titulo") String titulo, @Param("publisherId") Long publisherId);

    Optional<Livro> findByTitulo(String titulo);

    @Query("SELECT l FROM Livro l WHERE l.titulo LIKE %:titulo%")
    List<Livro> findByTituloContaining(@Param("titulo") String titulo);

    @Query("SELECT l FROM Livro l WHERE l.preco BETWEEN :precoMin AND :precoMax")
    List<Livro> findByPrecoBetween(@Param("precoMin") java.math.BigDecimal precoMin, @Param("precoMax") java.math.BigDecimal precoMax);
}
