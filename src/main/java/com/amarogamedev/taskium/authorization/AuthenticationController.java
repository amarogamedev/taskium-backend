package com.amarogamedev.taskium.authorization;

import com.amarogamedev.taskium.dto.UsuarioLoginDTO;
import com.amarogamedev.taskium.dto.UsuarioRegistroDTO;
import com.amarogamedev.taskium.entity.Usuario;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PermitAll
    @PostMapping(value = "/login")
    public ResponseEntity<String> login (@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(usuarioLoginDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
    }

    @PermitAll
    @PostMapping(value = "/registrar")
    public ResponseEntity<String> registrar (@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        try {
            Usuario usuario = authenticationService.registrar(usuarioRegistroDTO);
            UsuarioLoginDTO loginDTO = new UsuarioLoginDTO(usuario.getEmail(), usuarioRegistroDTO.senha());
            return ResponseEntity.ok(authenticationService.login(loginDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}