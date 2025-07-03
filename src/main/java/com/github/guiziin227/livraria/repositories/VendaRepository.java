package com.github.guiziin227.livraria.repositories;

import com.github.guiziin227.livraria.model.Venda;
import com.github.guiziin227.livraria.model.PK.VendaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, VendaPK> {

    @Query("SELECT v FROM Venda v WHERE v.id.clienteId = :clienteId")
    List<Venda> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT v FROM Venda v WHERE v.id.livroId = :livroId")
    List<Venda> findByLivroId(@Param("livroId") Long livroId);

    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim")
    List<Venda> findByDataVendaBetween(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

    @Query("SELECT v FROM Venda v WHERE v.notaFiscal = :notaFiscal")
    List<Venda> findByNotaFiscal(@Param("notaFiscal") String notaFiscal);
}
