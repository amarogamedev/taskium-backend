package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Board;

import java.util.Set;
import java.util.stream.Collectors;

public record BoardDTO(
    Long id,
    String key,
    String name,
    UserDTO owner,
    Set<UserDTO> members,
    Integer taskCount
) {
    public static BoardDTO fromEntity(Board board, Integer taskCount) {
        return new BoardDTO(
                board.getId(),
                board.getKey(),
                board.getName(),
                board.getOwner() != null ? UserDTO.fromEntity(board.getOwner()) : null,
                board.getMembers() != null ? board.getMembers().stream().map(UserDTO::fromEntity).collect(Collectors.toSet()) : null,
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
