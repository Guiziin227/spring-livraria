package com.github.guiziin227.livraria.dto.responses;

import com.github.guiziin227.livraria.dto.simples.LivroSimpleDTO;
import com.github.guiziin227.livraria.dto.simples.CategoriaSimpleDTO;

/**
 * DTO para respostas de LivroCategoria
 */
public record LivroCategoriaResponseDTO(
        LivroSimpleDTO livro,
        CategoriaSimpleDTO categoria
) {

}
