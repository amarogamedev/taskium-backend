package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.BoardDTO;
import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Autowired
    @Lazy
    TaskService taskService;

    @Autowired
    UserService userService;

    public List<BoardDTO> findAllUserBoards() {
        Long userId = UserService.getLoggedUser().getId();
        List<Board> ownedBoards = repository.findByOwnerId(userId);
        List<Board> memberBoards = repository.findByMembersId(userId);

        return Stream.concat(ownedBoards.stream(), memberBoards.stream())
                .distinct()
                .map(board -> BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size()))
                .collect(Collectors.toList());
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
        User loggedUser = UserService.getLoggedUser();
        if(board.getOwner() != loggedUser && !board.getMembers().contains(loggedUser)) {
            throw new IllegalArgumentException("You do not have permission to access this board");
        }
        return BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size());
    }

    public BoardDTO addMember(Long boardId, String userLogin) {
        Board board = findBoardById(boardId);
        User loggedUser = UserService.getLoggedUser();

        if (!board.getOwner().equals(loggedUser)) {
            throw new IllegalArgumentException("Only the board owner can add members");
        }

        User newMember = userService.findUserByLogin(userLogin);
        if (newMember == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (board.getMembers() == null) {
            board.setMembers(new HashSet<>());
        }

        board.getMembers().add(newMember);
        board = repository.save(board);

        return BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size());
    }

    public BoardDTO removeMember(Long boardId, String userLogin) {
        Board board = findBoardById(boardId);
        User loggedUser = UserService.getLoggedUser();

        if (!board.getOwner().equals(loggedUser)) {
            throw new IllegalArgumentException("Only the board owner can remove members");
        }

        User memberToRemove = userService.findUserByLogin(userLogin);
        if (memberToRemove == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (board.getMembers() == null || !board.getMembers().contains(memberToRemove)) {
            throw new IllegalArgumentException("User is not a member of this board");
        }

        board.getMembers().remove(memberToRemove);
        board = repository.save(board);

        return BoardDTO.fromEntity(board, taskService.getTasksByBoardId(board.getId()).size());
    }
}