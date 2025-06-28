package com.amarogamedev.taskium.entity;

import com.amarogamedev.taskium.enums.Priority;
import com.amarogamedev.taskium.enums.TaskStatus;
import com.amarogamedev.taskium.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_id", nullable = false, unique = true)
    private Long internalId;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false, updatable = false)
    private User createdByUser;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private TaskStatus status;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "completed_date")
    private Date completedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaskType type;

    @JoinColumn(name = "board_id")
    @JsonIgnore
    @ManyToOne
    private Board board;

    @JoinColumn(name = "parent_task_id")
    @ManyToOne
    private Task parentTask;
}
