package com.amarogamedev.taskium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_user_id", nullable = false, updatable = false)
    private User author;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
