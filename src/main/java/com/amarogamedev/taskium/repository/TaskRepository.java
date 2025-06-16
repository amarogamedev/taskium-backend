package com.amarogamedev.taskium.repository;

import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBoard(Board board);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByAssignedUser(User user);
    List<Task> findByBoardAndStatus(Board board, TaskStatus status);
    List<Task> findByParentTask(Task parentTask);
}
