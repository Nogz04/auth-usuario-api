package br.auth.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioRequestDTO(


        @NotBlank(message = "Por favor, informe seu e-mail para login.")
        @Email(message = "e-mail inválido")
        String email,

        @NotBlank(message = "Por favor, insira sua senha")
        String senha
)
{}
