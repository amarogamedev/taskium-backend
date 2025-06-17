package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Board;

public record BoardDTO(
    Long id,
    String key,
    String name,
    Long ownerId,
    Integer taskCount
) {
    public static BoardDTO fromEntity(Board board, Integer taskCount) {
        return new BoardDTO(
                board.getId(),
                board.getKey(),
                board.getName(),
                board.getOwner() != null ? board.getOwner().getId() : null,
                taskCount
        );
    }

    public static Board toEntity(BoardDTO boardDTO) {
        Board board = new Board();
        board.setId(boardDTO.id());
        board.setKey(boardDTO.key());
        board.setName(boardDTO.name());
        return board;
    }
}
