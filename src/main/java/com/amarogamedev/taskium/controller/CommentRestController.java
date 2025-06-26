package com.amarogamedev.taskium.controller;

import com.amarogamedev.taskium.dto.CommentDTO;
import com.amarogamedev.taskium.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Log4j2
public class CommentRestController {

    @Autowired
    CommentService commentService;

    @GetMapping(value = "/task/{taskId}")
    public ResponseEntity<?> getCommentsByTaskId(@PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(commentService.getCommentsByTaskId(taskId));
        } catch (Exception e) {
            log.error("An error occurred while searching for comments on task with id: {}", taskId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO createdComment = commentService.createComment(commentDTO);
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            log.error("An error occurred while creating a comment on task {}", commentDTO.taskId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("An error occurred while deleting the comment with id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
