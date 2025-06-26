package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.CommentDTO;
import com.amarogamedev.taskium.entity.Comment;
import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    @Lazy
    private TaskService taskService;

    public CommentDTO createComment(CommentDTO dto) {
        Comment comment = CommentDTO.toEntity(dto);
        Task task = taskService.getTaskById(dto.taskId());
        
        comment.setTask(task);
        comment.setAuthor(UserService.getLoggedUser());
        comment.setCreationDateTime(LocalDateTime.now());

        return CommentDTO.fromEntity(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        validateCommentAuthor(comment);
        commentRepository.deleteById(id);
    }

    public List<CommentDTO> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream()
                .map(CommentDTO::fromEntity)
                .toList();
    }

    private void validateCommentAuthor(Comment comment) {
        if (!comment.getAuthor().equals(UserService.getLoggedUser())) {
            throw new RuntimeException("User is not the author of this comment");
        }
    }
}
