package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.enums.Priority;
import com.amarogamedev.taskium.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(
    Long id,
    Long assignedUserId,
    Long createdByUserId,
    TaskStatus status,
    String title,
    String description,
    LocalDateTime creationDate,
    LocalDateTime dueDate,
    LocalDateTime completedDate,
    Priority priority,
    Long parentTaskId,
    Long boardId
) {
    public static TaskDTO fromEntity(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getAssignedUser() != null ? task.getAssignedUser().getId() : null,
            task.getCreatedByUser() != null ? task.getCreatedByUser().getId() : null,
            task.getStatus(),
            task.getTitle(),
            task.getDescription(),
            task.getCreationDate(),
            task.getDueDate(),
            task.getCompletedDate(),
            task.getPriority(),
            task.getParentTask() != null ? task.getParentTask().getId() : null,
            task.getBoard() != null ? task.getBoard().getId() : null
        );
    }

    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.id());
        task.setStatus(taskDTO.status());
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCreationDate(taskDTO.creationDate());
        task.setDueDate(taskDTO.dueDate());
        task.setCompletedDate(taskDTO.completedDate());
        task.setPriority(taskDTO.priority());
        return task;
    }
}
