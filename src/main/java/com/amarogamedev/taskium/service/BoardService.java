package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.BoardDTO;
import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Autowired
    @Lazy
    TaskService taskService;

    public List<BoardDTO> findAllBoardsByOwner() {
        List<BoardDTO> boards = new ArrayList<>();
        repository.findByOwnerId(UserService.getLoggedUser().getId()).forEach(board -> {
            boards.add(BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size()));
        });
        return boards;
    }

    public Board findBoardById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public BoardDTO saveBoard(BoardDTO boardDTO) {
        Board board = BoardDTO.toEntity(boardDTO);
        board.setOwner(UserService.getLoggedUser());
        return BoardDTO.fromEntity(repository.save(board), 0);
    }

    public BoardDTO getBoardById(Long id) {
        Board board = findBoardById(id);
        return BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size());
    }
}