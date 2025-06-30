package com.github.guiziin227.livraria.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para respostas de Livro
 */
public record LivroResponseDTO(
        Long id,
        String titulo,
        String resumo,
        String date,
        BigDecimal preco,
        EditoraResponseDTO publisher,
        List<CategoriaSimpleDTO> category,
        List<AutorSimpleDTO> authors
) {
}
