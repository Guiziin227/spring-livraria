package com.github.guiziin227.livraria.dto.requests;

import com.github.guiziin227.livraria.dto.simples.AutorSimpleDTO;
import com.github.guiziin227.livraria.dto.simples.CategoriaSimpleDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação e atualização de Livro
 */
public record LivroRequestDTO(
        @NotBlank(message = "Título não pode estar vazio")
        @Size(max = 100, message = "Título não pode ter mais de 100 caracteres")
        String titulo,

        @NotBlank(message = "Resumo não pode estar vazio")
        @Size(max = 1000, message = "Resumo não pode ter mais de 1000 caracteres")
        String resumo,

        @NotNull(message = "Data não pode ser nula")
        String date,

        @NotNull(message = "Preço não pode ser nulo")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal preco,

        @NotNull(message = "Publisher não pode ser nulo")
        PublisherSimpleDTO publisher,

        @NotNull(message = "Categoria não pode ser nula")
        CategoriaSimpleDTO categoria,

        @NotNull(message = "Autor não pode ser nulo")
        AutorSimpleDTO autor
) {
    public record PublisherSimpleDTO(@NotNull Long id) {}
}
