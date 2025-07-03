package com.github.guiziin227.livraria.model;

import com.github.guiziin227.livraria.model.PK.VendaPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VendaPK id;

    @Column(length = 45, nullable = false)
    private String notaFiscal;

    @Column(nullable = false)
    private Date dataVenda;

    @Column(nullable = false)
    private Double valorTotal;

}
