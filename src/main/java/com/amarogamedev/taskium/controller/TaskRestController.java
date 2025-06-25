package com.amarogamedev.taskium.controller;

import com.amarogamedev.taskium.dto.TaskDTO;
import com.amarogamedev.taskium.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Log4j2
public class TaskRestController {

    @Autowired
    TaskService taskService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.getTaskById(id));
        } catch (Exception e) {
            log.error("An error occurred while searching for the task with id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/board/{boardId}")
    public ResponseEntity<?> getTasksByBoardId(@PathVariable Long boardId) {
        try {
            return ResponseEntity.ok(taskService.getTasksByBoardIdWith5DaysLimit(boardId));
        } catch (Exception e) {
            log.error("An error occurred while searching for tasks on board with id: {}", boardId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/board/{boardId}/all")
    public ResponseEntity<?> getAllTasksByBoardId(@PathVariable Long boardId) {
        try {
            return ResponseEntity.ok(taskService.getTasksByBoardId(boardId));
        } catch (Exception e) {
            log.error("An error occurred while searching for tasks on board with id: {}", boardId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO createdTask = taskService.createTask(taskDTO);
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            log.error("An error occurred while creating a task on board {}", taskDTO.boardId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            log.error("An error occurred while updating the task with id: {}", taskDTO.id(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("An error occurred while deleting the task with id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
