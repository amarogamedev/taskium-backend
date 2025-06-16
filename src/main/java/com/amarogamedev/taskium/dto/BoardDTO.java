package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Board;

public record BoardDTO(
    Long id,
    String key,
    String name,
    Long ownerId
) {
    public static BoardDTO toEntity(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getKey(),
                board.getName(),
                board.getOwner() != null ? board.getOwner().getId() : null
        );
    }
}
