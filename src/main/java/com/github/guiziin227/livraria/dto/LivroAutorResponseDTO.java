package com.github.guiziin227.livraria.dto;

/**
 * DTO para respostas de LivroCategoria
 */
public record LivroAutorResponseDTO(
        LivroSimpleDTO livro,
        AutorSimpleDTO autor
) {

}
