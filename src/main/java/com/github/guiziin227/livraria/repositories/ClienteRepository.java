package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
