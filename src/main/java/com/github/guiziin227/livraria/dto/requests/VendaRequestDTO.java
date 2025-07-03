package com.github.guiziin227.livraria.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record VendaRequestDTO(

        @NotNull(message = "O ID do cliente é obrigatório")
        @Positive(message = "O ID do cliente deve ser positivo")
        Long clienteId,

        @NotNull(message = "O ID do livro é obrigatório")
        @Positive(message = "O ID do livro deve ser positivo")
        Long livroId,

        @NotBlank(message = "A nota fiscal é obrigatória")
        @Size(max = 45, message = "A nota fiscal não pode ter mais de 45 caracteres")
        String notaFiscal,

        @NotNull(message = "A data da venda é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataVenda,

        @NotNull(message = "O valor total é obrigatório")
        @Positive(message = "O valor total deve ser positivo")
        Double valorTotal
) {
}
