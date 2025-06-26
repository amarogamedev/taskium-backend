package com.amarogamedev.taskium.repository;

import com.amarogamedev.taskium.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
}
