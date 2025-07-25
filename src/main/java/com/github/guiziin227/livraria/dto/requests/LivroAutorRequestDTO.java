package com.github.guiziin227.livraria.dto.requests;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para requisições de associação entre Livro e Categoria
 */
public record LivroAutorRequestDTO(
        @NotNull(message = "ID do livro não pode ser nulo")
        Long livroId,

        @NotNull(message = "ID da categoria não pode ser nulo")
        Long autorId
) {
}
