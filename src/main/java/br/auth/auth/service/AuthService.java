package br.auth.auth.service;

import br.auth.auth.dto.LoginUsuarioRequestDTO;
import br.auth.auth.dto.LoginUsuarioResponseDTO;
import br.auth.auth.model.Usuario;
import br.auth.auth.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public LoginUsuarioResponseDTO autenticar(LoginUsuarioRequestDTO dados) {
        Usuario usuario = usuarioRepository.findByEmail(dados.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        if (!passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha incorreta.");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginUsuarioResponseDTO(token);
    }
}
