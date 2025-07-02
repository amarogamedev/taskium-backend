package com.amarogamedev.taskium.auth;

import com.amarogamedev.taskium.auth.dto.UserLoginDTO;
import com.amarogamedev.taskium.auth.dto.UserRegisterDTO;
import com.amarogamedev.taskium.auth.service.AuthenticationService;
import com.amarogamedev.taskium.entity.User;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PermitAll
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestBody UserLoginDTO userLoginDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(userLoginDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PermitAll
    @PostMapping(value = "/register")
    public ResponseEntity<Object> register (@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User user = authenticationService.register(userRegisterDTO);
            UserLoginDTO loginDTO = new UserLoginDTO(user.getLogin(), userRegisterDTO.password());
            return ResponseEntity.ok(authenticationService.login(loginDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user");
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User updatedUser = authenticationService.updateUser(userRegisterDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user");
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        try {
            authenticationService.logout(token);
            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during logout");
        }
    }
}