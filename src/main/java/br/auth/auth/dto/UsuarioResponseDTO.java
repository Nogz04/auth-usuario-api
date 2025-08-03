package br.auth.auth.dto;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        String role
)
{}
