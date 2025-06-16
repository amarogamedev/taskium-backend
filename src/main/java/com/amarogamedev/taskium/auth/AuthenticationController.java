package com.amarogamedev.taskium.auth;

import com.amarogamedev.taskium.auth.dto.UserLoginDTO;
import com.amarogamedev.taskium.auth.dto.UserRegisterDTO;
import com.amarogamedev.taskium.auth.service.AuthenticationService;
import com.amarogamedev.taskium.entity.User;
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
    public ResponseEntity<String> login (@RequestBody UserLoginDTO userLoginDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(userLoginDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PermitAll
    @PostMapping(value = "/register")
    public ResponseEntity<String> register (@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User user = authenticationService.register(userRegisterDTO);
            UserLoginDTO loginDTO = new UserLoginDTO(user.getLogin(), userRegisterDTO.password());
            return ResponseEntity.ok(authenticationService.login(loginDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}