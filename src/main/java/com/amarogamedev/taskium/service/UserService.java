package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByLogin(String login) {
        return Objects.requireNonNull(userRepository.findByLogin(login));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
}
