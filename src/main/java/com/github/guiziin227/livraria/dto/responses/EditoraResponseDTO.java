package com.github.guiziin227.livraria.dto.responses;

/**
 * DTO para respostas de Editora
 */
public record EditoraResponseDTO(
        Long id,
        String name,
        String cnpj,
        String city,
        String country
) {}
