package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.enums.Priority;
import com.amarogamedev.taskium.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record TaskDTO(
    Long id,
    Long assignedUserId,
    String assignedUserName,
    Long createdByUserId,
    String createdByUserName,
    TaskStatus status,
    String title,
    String description,
    Date creationDate,
    Date dueDate,
    Date completedDate,
    Priority priority,
    Long parentTaskId,
    Long boardId,
    List<TaskDTO> subtasks
) {
    public static TaskDTO fromEntity(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getAssignedUser() != null ? task.getAssignedUser().getId() : null,
            task.getAssignedUser() != null ? task.getAssignedUser().getName() : null,
            task.getCreatedByUser() != null ? task.getCreatedByUser().getId() : null,
            task.getCreatedByUser() != null ? task.getCreatedByUser().getName() : null,
            task.getStatus(),
            task.getTitle(),
            task.getDescription(),
            task.getCreationDate(),
            task.getDueDate(),
            task.getCompletedDate(),
            task.getPriority(),
            task.getParentTask() != null ? task.getParentTask().getId() : null,
            task.getBoard() != null ? task.getBoard().getId() : null,
            task.getSubtasks().stream().map(TaskDTO::fromEntity).toList()
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
