package com.amarogamedev.taskium.service;

import com.amarogamedev.taskium.dto.BoardDTO;
import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.entity.User;
import com.amarogamedev.taskium.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Autowired
    UserService userService;

    public List<BoardDTO> findAllUserBoards() {
        List<Board> boards = repository.findByUser(UserService.getLoggedUser());
        return boards.stream().map(BoardDTO::fromEntity).collect(Collectors.toList());
    }

    public Board findBoardById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Board findBoardByKey(String key) {
        return repository.findByKeyAndUser(key, UserService.getLoggedUser()).orElseThrow();
    }

    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = BoardDTO.toEntity(boardDTO);
        board.setOwner(UserService.getLoggedUser());
        return BoardDTO.fromEntity(repository.save(board));
    }

    public BoardDTO getBoardByKey(String key) {
        Board board = findBoardByKey(key);
        validateMember(board);
        return BoardDTO.fromEntity(board);
    }

    public void validateMember(Board board) {
        User loggedUser = UserService.getLoggedUser();
        boolean isMember = board.getMembers() != null && board.getMembers().stream().anyMatch(member -> Objects.equals(member.getId(), loggedUser.getId()));
        if(!Objects.equals(board.getOwner().getId(), loggedUser.getId()) && !isMember) {
            throw new IllegalArgumentException("You do not have permission to access this board");
        }
    }

    public BoardDTO addMember(String key, String userLogin) {
        Board board = findBoardByKey(key);
        User loggedUser = UserService.getLoggedUser();

        if (!Objects.equals(board.getOwner().getId(), loggedUser.getId())) {
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

        return BoardDTO.fromEntity(board);
    }

    public BoardDTO removeMember(String key, String userLogin) {
        Board board = findBoardByKey(key);
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

        return BoardDTO.fromEntity(board);
    }
}