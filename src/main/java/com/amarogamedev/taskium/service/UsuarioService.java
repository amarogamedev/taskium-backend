package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.entity.Usuario;
import com.amarogamedev.taskium.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario buscarUsuario (String email) {
        return Objects.requireNonNull(usuarioRepository.findByEmail(email));
    }

    public Usuario salvarUsuario (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean usuarioExiste (String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
}
