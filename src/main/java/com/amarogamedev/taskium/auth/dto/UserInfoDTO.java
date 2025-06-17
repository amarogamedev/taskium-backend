package com.amarogamedev.taskium.auth.dto;

public record UserInfoDTO(String token, String login, String name, Long id) { }