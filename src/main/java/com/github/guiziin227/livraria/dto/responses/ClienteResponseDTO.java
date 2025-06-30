package com.github.guiziin227.livraria.dto.responses;

public record ClienteResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        String cep
) {
}
