package com.github.guiziin227.livraria.dto;

/**
 * DTO para respostas de LivroCategoria
 */
public record LivroCategoriaResponseDTO(
        LivroSimpleDTO livro,
        CategoriaSimpleDTO categoria
) {

}
