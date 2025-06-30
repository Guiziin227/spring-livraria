package com.github.guiziin227.livraria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorRequestDTO(

        @NotBlank
        @Size(max=100, message = "Nome não pode ter mais de 100 caracteres")
        String name,

        @NotBlank
        @Size(max=70, message = "Nacionalidade não pode ter mais de 70 caracteres")
        String nacionality,

        @NotBlank
        @Size(max=1000, message = "Biografia não pode ter mais de 1000 caracteres")
        String biography
) {
}
