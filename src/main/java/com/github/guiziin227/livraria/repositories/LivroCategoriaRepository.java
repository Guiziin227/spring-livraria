package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroCategoriaRepository extends JpaRepository<LivroCategoria, LivroCategoriaPK> {

    @Query("SELECT lc FROM LivroCategoria lc WHERE lc.livro.id = :livroId")
    List<LivroCategoria> findByLivroId(@Param("livroId") Long livroId);

    @Query("SELECT lc FROM LivroCategoria lc WHERE lc.categoria.id = :categoriaId")
    List<LivroCategoria> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("SELECT CASE WHEN COUNT(lc) > 0 THEN true ELSE false END FROM LivroCategoria lc WHERE lc.livro.id = :livroId AND lc.categoria.id = :categoriaId")
    boolean existsByLivroIdAndCategoriaId(@Param("livroId") Long livroId, @Param("categoriaId") Long categoriaId);
}
