package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.enums.UserRole;

public record UserDTO(
    Long id,
    String name,
    String login,
    UserRole userRole
) {
    public static UserDTO fromEntity(com.amarogamedev.taskium.entity.User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getLogin(),
            user.getUserRole()
        );
    }

    public static com.amarogamedev.taskium.entity.User toEntity(UserDTO userDTO) {
        com.amarogamedev.taskium.entity.User user = new com.amarogamedev.taskium.entity.User();
        user.setId(userDTO.id());
        user.setName(userDTO.name());
        user.setLogin(userDTO.login());
        user.setUserRole(userDTO.userRole());
        return user;
    }
}
