package com.amarogamedev.taskium.dto;

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
) {}
