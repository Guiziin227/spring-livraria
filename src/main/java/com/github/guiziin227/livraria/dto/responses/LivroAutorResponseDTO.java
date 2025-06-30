package com.github.guiziin227.livraria.dto.responses;

import com.github.guiziin227.livraria.dto.simples.LivroSimpleDTO;
import com.github.guiziin227.livraria.dto.simples.AutorSimpleDTO;

/**
 * DTO para respostas de LivroCategoria
 */
public record LivroAutorResponseDTO(
        LivroSimpleDTO livro,
        AutorSimpleDTO autor
) {

}
