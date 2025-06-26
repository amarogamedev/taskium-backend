package com.amarogamedev.taskium.repository;

import com.amarogamedev.taskium.entity.Board;
import com.amarogamedev.taskium.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE :user = b.owner OR :user MEMBER OF b.members")
    List<Board> findByUser(@Param("user") User user);

    @Query("SELECT b FROM Board b WHERE b.key = :key AND (:user = b.owner OR :user MEMBER OF b.members)")
    Optional<Board> findByKeyAndUser(@Param("key") String key, @Param("user") User user);
}
