package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.BoardDTO;
import com.amarogamedev.taskium.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    public List<BoardDTO> findAllBoardsByOwner() {
        List<BoardDTO> boards = new ArrayList<>();
        repository.findByOwnerId(UserService.getLoggedUser().getId()).forEach(board -> {
            boards.add(BoardDTO.toEntity(board));
        });
        return boards;
    }
}


