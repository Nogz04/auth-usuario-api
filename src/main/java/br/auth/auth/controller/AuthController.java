package br.auth.auth.controller;

import br.auth.auth.dto.LoginUsuarioRequestDTO;
import br.auth.auth.dto.LoginUsuarioResponseDTO;
import br.auth.auth.dto.UsuarioRequestDTO;
import br.auth.auth.dto.UsuarioResponseDTO;
import br.auth.auth.model.Role;
import br.auth.auth.model.Usuario;
import br.auth.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dados) {
        Usuario novoUsuario = Usuario.builder()
                .nome(dados.nome())
                .email(dados.email())
                .senha(passwordEncoder.encode(dados.senha())) // **Importante:** Codifica a senha antes de salvar no banco.
                .role(Role.USER) // Define um papel padrão para todos os novos usuários.
                .build();

        Usuario usuarioSalvo = authService.cadastrarUsuario(novoUsuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUsuarioResponseDTO> login(@RequestBody @Valid LoginUsuarioRequestDTO dados) {
        LoginUsuarioResponseDTO response = authService.autenticar(dados);
        return ResponseEntity.ok(response);
    }
}