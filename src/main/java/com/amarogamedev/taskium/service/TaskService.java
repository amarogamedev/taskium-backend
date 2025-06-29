package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.TaskDTO;
import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.entity.Task;
import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.enums.TaskStatus;
import com.amarogamedev.taskium.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    @Lazy
    CommentService commentService;

    public TaskDTO getTaskDTOById(Long id) {
        return TaskDTO.fromEntity(taskRepository.findById(id).orElseThrow());
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public TaskDTO createTask(TaskDTO dto) {
        Task task = TaskDTO.toEntity(dto);
        Board board = boardService.findBoardById(dto.boardId());
        boardService.validateMember(board);

        task.setBoard(board);
        task.setInternalId(getNextInternalIdForBoard(task.getBoard().getId()));
        task.setCreationDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        task.setCreatedByUser(UserService.getLoggedUser());
        task.setCompletedDate(TaskStatus.DONE.equals(task.getStatus()) ? Date.valueOf(LocalDateTime.now().toLocalDate()) : null);

        if (dto.assignedUserId() != null) {
            task.setAssignedUser(userService.findUserById(dto.assignedUserId()));
        }

        if(dto.parentTaskId() != null) {
            task.setParentTask(getTaskById(dto.parentTaskId()));
        }

        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long taskId, TaskDTO dto) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        boardService.validateMember(task.getBoard());

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueDate(dto.dueDate());
        task.setStatus(dto.status());
        task.setPriority(dto.priority());
        task.setType(dto.type());

        if (dto.assignedUserId() != null) {
            task.setAssignedUser(userService.findUserById(dto.assignedUserId()));
        }

        if (TaskStatus.DONE.equals(dto.status())) {
            task.setCompletedDate(Date.valueOf(LocalDate.now()));
        } else {
            task.setCompletedDate(null);
        }

        return TaskDTO.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        boardService.validateMember(task.getBoard());
        deleteSubtasks(task);
        commentService.deleteCommentsByTask(task);
        taskRepository.deleteById(id);
    }

    @Transactional
    void deleteSubtasks(Task task) {
        List<Task> subtasks = getSubtasksByTaskId(task.getId());
        subtasks.forEach(subtask -> {
            deleteSubtasks(subtask);
            commentService.deleteCommentsByTask(subtask);
            taskRepository.deleteById(subtask.getId());
        });
    }

    public List<TaskDTO> getTasksByBoardKeyWith5DaysLimit(String boardKey) {
        Long boardId = boardService.findBoardByKey(boardKey).getId();
        Date sevenDaysAgo = Date.valueOf(LocalDateTime.now().minusDays(5L).toLocalDate());
        return taskRepository.findByBoardIdAndDateLimit(boardId, sevenDaysAgo).stream().map(TaskDTO::fromEntity).toList();
    }

    public List<TaskDTO> getTasksByBoardKey(String boardKey) {
        Long boardId = boardService.findBoardByKey(boardKey).getId();
        return taskRepository.findByBoardId(boardId).stream().map(TaskDTO::fromEntity).toList();
    }

    public List<TaskDTO> getSubtasksDTOByTaskId(Long taskId) {
        return taskRepository.findByParentTaskId(taskId).stream().map(TaskDTO::fromEntity).toList();
    }

    public List<Task> getSubtasksByTaskId(Long taskId) {
        return taskRepository.findByParentTaskId(taskId);
    }

    public Long getNextInternalIdForBoard(Long boardId) {
        Long maxInternalId = taskRepository.findMaxInternalIdByBoardId(boardId);
        if (maxInternalId == null) {
            return 1L;
        }
        return maxInternalId + 1;
    }
}
