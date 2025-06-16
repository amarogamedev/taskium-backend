package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.TaskDTO;
import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserService userService;

    @Autowired
    BoardService boardService;

    public TaskDTO getTaskById(Long id) {
        return TaskDTO.fromEntity(taskRepository.findById(id).orElseThrow());
    }

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = TaskDTO.toEntity(taskDTO);
        task.setCreatedByUser(UserService.getLoggedUser());
        task.setBoard(boardService.findBoardById(taskDTO.boardId()));

        if(taskDTO.assignedUserId() != null) {
            task.setAssignedUser(userService.findUserById(taskDTO.assignedUserId()));
        }
        if(taskDTO.parentTaskId() != null) {
            task.setParentTask(taskRepository.findById(taskDTO.parentTaskId()).orElseThrow());
        }

        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Object getTasksByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId).stream().map(TaskDTO::fromEntity).toList();
    }
}
