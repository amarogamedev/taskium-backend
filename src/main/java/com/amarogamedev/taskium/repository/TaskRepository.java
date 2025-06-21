package com.amarogamedev.taskium.repository;

import com.amarogamedev.taskium.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(" SELECT t FROM Task t WHERE t.board.id = :boardId AND (t.status != 'DONE' OR (t.completedDate IS NULL OR t.completedDate >= :dateLimit)) ORDER BY t.id DESC")
    List<Task> findByBoardIdAndDateLimit(@Param("boardId") Long boardId, @Param("dateLimit") Date dateLimit);
}
