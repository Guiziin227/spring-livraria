package com.github.guiziin227.livraria.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisições de criação e atualização de Editora
 */
public record EditoraRequestDTO(
        @NotBlank(message = "Nome não pode estar vazio")
        @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
        String name,

        @NotBlank(message = "CNPJ não pode estar vazio")
        @Size(max = 18, message = "CNPJ não pode ter mais de 18 caracteres")
        String cnpj,

        @NotBlank(message = "Cidade não pode estar vazia")
        @Size(max = 50, message = "Cidade não pode ter mais de 50 caracteres")
        String city,

        @NotBlank(message = "País não pode estar vazio")
        @Size(max = 50, message = "País não pode ter mais de 50 caracteres")
        String country
) {}
