package com.github.guiziin227.livraria.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record VendaResponseDTO(
        Long clienteId,
        Long livroId,
        String notaFiscal,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataVenda,
        Double valorTotal
) {
}
