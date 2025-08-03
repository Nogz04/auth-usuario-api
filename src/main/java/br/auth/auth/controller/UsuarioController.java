package br.auth.auth.controller;

import br.auth.auth.dto.UsuarioRequestDTO;
import br.auth.auth.dto.UsuarioResponseDTO;
import br.auth.auth.model.Usuario;
import br.auth.auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {

        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();

        List<UsuarioResponseDTO> response = usuarios.stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getRole().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioResponseDTO response = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dados) {

        Usuario dadosAtualizados = new Usuario();
        dadosAtualizados.setNome(dados.nome());
        dadosAtualizados.setEmail(dados.email());

        // A senha deve ser codificada novamente ao ser atualizada
        dadosAtualizados.setSenha(passwordEncoder.encode(dados.senha()));

        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dadosAtualizados);

        UsuarioResponseDTO response = new UsuarioResponseDTO(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();

    }
}