package com.amarogamedev.taskium.authorization;

import com.amarogamedev.taskium.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("taskium-auth")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT: " + e.getMessage());
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("taskium-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}
