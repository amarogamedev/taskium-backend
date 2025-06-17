package com.amarogamedev.taskium.auth.service;

import com.amarogamedev.taskium.auth.dto.UserInfoDTO;
import com.amarogamedev.taskium.auth.dto.UserLoginDTO;
import com.amarogamedev.taskium.auth.dto.UserRegisterDTO;
import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.enums.UserRole;
import com.amarogamedev.taskium.service.UserService;
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
    UserService userService;

    @Autowired
    TokenService tokenService;

    public UserInfoDTO login(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                userLoginDTO.login(),
                userLoginDTO.password()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) authentication.getPrincipal());
        User user = userService.findUserByLogin(userLoginDTO.login());
        return new UserInfoDTO(token, user.getLogin(), user.getName(), user.getId());
    }

    public User register(UserRegisterDTO userRegisterDTO) {
        validateUserAlreadyRegistered(userRegisterDTO.login());
        validatePassword(userRegisterDTO.password());

        String cryptoPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.password());

        User user = new User();
        user.setPassword(cryptoPassword);
        user.setLogin(userRegisterDTO.login());
        user.setName(userRegisterDTO.name());
        user.setUserRole(UserRole.USER);
        return userService.saveUser(user);
    }

    void validateUserAlreadyRegistered(String login) {
        if(userService.userExists(login)) {
            throw new IllegalArgumentException("There is already an user registered with this email");
        }
    }

    void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("The password must have at least 8 characters");
        }
    }

    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenService.invalidateToken(token);
        }
    }
}
