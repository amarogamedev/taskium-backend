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
            return ResponseEntity.ok(boardService.findAllUserBoards());
        }
        catch (Exception e) {
            log.error("An error ocurred while searching for all the boards", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{boardKey}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable String boardKey) {
        try {
            return ResponseEntity.ok(boardService.getBoardByKey(boardKey));
        } catch (Exception e) {
            log.error("An error occurred while searching for the board with key: {}", boardKey, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) {
        try {
            BoardDTO createdBoard = boardService.createBoard(boardDTO);
            return ResponseEntity.ok(createdBoard);
        } catch (Exception e) {
            log.error("An error occurred while creating a board", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{boardKey}/members/{userLogin}")
    public ResponseEntity<BoardDTO> addMember(@PathVariable String boardKey, @PathVariable String userLogin) {
        try {
            return ResponseEntity.ok(boardService.addMember(boardKey, userLogin));
        } catch (IllegalArgumentException e) {
            log.error("Error adding member to board: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("An error occurred while adding member to board", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{boardKey}/members/{userLogin}")
    public ResponseEntity<BoardDTO> removeMember(@PathVariable String boardKey, @PathVariable String userLogin) {
        try {
            return ResponseEntity.ok(boardService.removeMember(boardKey, userLogin));
        } catch (IllegalArgumentException e) {
            log.error("Error removing member from board: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("An error occurred while removing member from board", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
