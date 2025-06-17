package com.amarogamedev.taskium.auth.service;

import com.amarogamedev.taskium.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    String secret;

    private Set<String> invalidatedTokens = new HashSet<>();

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("taskium-auth")
                    .withSubject(user.getLogin())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("An error ocurrued while generating the token: " + e.getMessage());
        }
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public String validateToken(String token) {
        if (invalidatedTokens.contains(token)) {
            throw new RuntimeException("Token has been invalidated");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("taskium-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
