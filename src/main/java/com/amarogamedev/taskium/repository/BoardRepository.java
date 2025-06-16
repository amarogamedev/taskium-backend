package com.amarogamedev.taskium.repository;

import com.amarogamedev.taskium.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByKey(String key);
    List<Board> findByOwnerId(Long ownerId);
}

