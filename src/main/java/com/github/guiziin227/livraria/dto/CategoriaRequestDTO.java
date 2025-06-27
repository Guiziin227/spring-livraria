package com.github.guiziin227.livraria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisições de criação e atualização de Categoria
 */
public record CategoriaRequestDTO(
        @NotBlank(message = "Nome não pode estar vazio")
        @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
        String name,

        @NotBlank(message = "Descrição não pode estar vazia")
        @Size(max = 500, message = "Descrição não pode ter mais de 500 caracteres")
        String description
) {
}
