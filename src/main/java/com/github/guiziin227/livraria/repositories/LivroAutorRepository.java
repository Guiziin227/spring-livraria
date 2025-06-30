package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.PK.LivroAutorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroAutorRepository extends JpaRepository<LivroAutor, LivroAutorPK> {

    @Query("SELECT la FROM LivroAutor la WHERE la.livro.id = :livroId")
    List<LivroAutor> findByLivroId(@Param("livroId") Long livroId);

    @Query("SELECT la FROM LivroAutor la WHERE la.autor.id = :autorId")
    List<LivroAutor> findByAutorId(@Param("autorId") Long autorId);

    @Query("SELECT la FROM LivroAutor la JOIN FETCH la.livro l LEFT JOIN FETCH l.livroCategories lc LEFT JOIN FETCH lc.categoria LEFT JOIN FETCH l.livroAutores la2 LEFT JOIN FETCH la2.autor WHERE la.autor.id = :autorId")
    List<LivroAutor> findByAutorIdWithCategories(@Param("autorId") Long autorId);

    @Query("SELECT CASE WHEN COUNT(la) > 0 THEN true ELSE false END FROM LivroAutor la WHERE la.livro.id = :livroId AND la.autor.id = :autorId")
    boolean existsByLivroIdAndAutorId(@Param("livroId") Long livroId,@Param("autorId") Long autorId);
}
