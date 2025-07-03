package com.github.guiziin227.livraria.dto.simples;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record VendaSimpleDTO(
        Long clienteId,
        Long livroId,
        String notaFiscal,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataVenda,
        Double valorTotal
) {
}
