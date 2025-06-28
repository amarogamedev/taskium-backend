package com.amarogamedev.taskium.dto;

import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.enums.Priority;
import com.amarogamedev.taskium.enums.TaskStatus;
import com.amarogamedev.taskium.enums.TaskType;

import java.util.Date;

public record TaskDTO(
    Long id,
    Long internalId,
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
    TaskType type,
    Long boardId,
    Long parentTaskId
) {
    public static TaskDTO fromEntity(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getInternalId(),
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
            task.getType(),
            task.getBoard() != null ? task.getBoard().getId() : null,
            task.getParentTask() != null ? task.getParentTask().getInternalId() : null
        );
    }

    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.id());
        task.setInternalId(taskDTO.internalId());
        task.setStatus(taskDTO.status());
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCreationDate(taskDTO.creationDate());
        task.setDueDate(taskDTO.dueDate());
        task.setCompletedDate(taskDTO.completedDate());
        task.setPriority(taskDTO.priority());
        task.setType(taskDTO.type());
        return task;
    }
}
