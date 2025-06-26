package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Comment;

import java.time.LocalDateTime;

public record CommentDTO(
    Long id,
    Long authorId,
    String authorName,
    String text,
    LocalDateTime creationDateTime,
    Long taskId
) {
    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
            comment.getId(),
            comment.getAuthor().getId(),
            comment.getAuthor().getName(),
            comment.getText(),
            comment.getCreationDateTime(),
            comment.getTask().getId()
        );
    }

    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.id());
        comment.setText(dto.text());
        return comment;
    }
}
