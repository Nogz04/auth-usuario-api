package br.auth.auth.service;


import br.auth.auth.model.Usuario;
import br.auth.auth.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("Não existe um usuário com esse email."));
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.
                findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new RuntimeException("Outro usuário já está usando este e-mail.");
        }

        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }

    public void removerUsuario(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}