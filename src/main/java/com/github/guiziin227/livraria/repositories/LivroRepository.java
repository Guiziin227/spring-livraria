package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
