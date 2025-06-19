package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.TaskDTO;
import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    @Lazy
    UserService userService;

    @Autowired
    @Lazy
    BoardService boardService;

    public TaskDTO getTaskById(Long id) {
        return TaskDTO.fromEntity(taskRepository.findById(id).orElseThrow());
    }

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = TaskDTO.toEntity(taskDTO);
        task.setCreatedByUser(UserService.getLoggedUser());
        task.setBoard(boardService.findBoardById(taskDTO.boardId()));
        task.setCreationDate(Date.valueOf(LocalDateTime.now().toLocalDate()));

        if(taskDTO.assignedUserId() != null) {
            task.setAssignedUser(userService.findUserById(taskDTO.assignedUserId()));
        }
        if(taskDTO.parentTaskId() != null) {
            task.setParentTask(taskRepository.findById(taskDTO.parentTaskId()).orElseThrow());
        }

        boardService.validateMember(task.getBoard());
        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        boardService.validateMember(task.getBoard());
        taskRepository.deleteById(id);
    }

    public List<TaskDTO> getTasksByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId).stream().map(TaskDTO::fromEntity).toList();
    }
}
