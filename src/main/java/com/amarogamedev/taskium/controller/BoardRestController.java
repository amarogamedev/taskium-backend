package com.amarogamedev.taskium.controller;

import com.amarogamedev.taskium.dto.BoardDTO;
import com.amarogamedev.taskium.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/board")
@Log4j2
public class BoardRestController {

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        try {
            return ResponseEntity.ok(boardService.findAllBoardsByOwner());
        }
        catch (Exception e) {
            log.error("An error ocurred while searching for all the boards", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(boardService.getBoardById(id));
        } catch (Exception e) {
            log.error("An error occurred while searching for the board with id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) {
        try {
            BoardDTO createdBoard = boardService.saveBoard(boardDTO);
            return ResponseEntity.ok(createdBoard);
        } catch (Exception e) {
            log.error("An error occurred while creating a board", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
