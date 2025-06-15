package com.amarogamedev.taskium.authorization;

import com.amarogamedev.taskium.dto.UsuarioLoginDTO;
import com.amarogamedev.taskium.dto.UsuarioRegistroDTO;
import com.amarogamedev.taskium.entity.Usuario;
import com.amarogamedev.taskium.enums.Tipo;
import com.amarogamedev.taskium.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TokenService tokenService;

    public String login(UsuarioLoginDTO usuarioLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                usuarioLoginDTO.email(),
                usuarioLoginDTO.senha()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        return tokenService.gerarToken((Usuario) authentication.getPrincipal());
    }

    public Usuario registrar(UsuarioRegistroDTO usuarioRegistroDTO) {
        validarUsuarioJaCadastrado(usuarioRegistroDTO.email());
        validarSenha(usuarioRegistroDTO.senha());

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioRegistroDTO.senha());

        Usuario usuario = new Usuario();
        usuario.setSenha(senhaCriptografada);
        usuario.setEmail(usuarioRegistroDTO.email());
        usuario.setNome(usuarioRegistroDTO.nome());
        usuario.setTipo(Tipo.USER);
        return usuarioService.salvarUsuario(usuario);
    }

    void validarUsuarioJaCadastrado(String email) {
        if(usuarioService.usuarioExiste(email)) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
    }

    void validarSenha(String senha) {
        if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres");
        }
        //TODO melhorar a validação da senha, incluindo letras maiúsculas, minúsculas, números e caracteres especiais
    }
}
