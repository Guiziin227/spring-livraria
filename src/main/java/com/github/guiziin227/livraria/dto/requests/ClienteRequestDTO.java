package com.github.guiziin227.livraria.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(


        @NotBlank
        @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @CPF(message = "CPF inválido")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas 11 dígitos")
        String cpf,

        @NotBlank
        @Size(min = 8 , max = 8, message = "O cep não pode ter mais de 15 caracteres")
        String cep
) {
}
