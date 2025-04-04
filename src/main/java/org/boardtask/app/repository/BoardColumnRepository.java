package org.boardtask.app.repository;

import java.util.Optional;

import org.boardtask.app.entity.BoardColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumnEntity, Long> {
    @Query(value = "select * from board_column where id = :columnId and board_id = :boardId", nativeQuery = true)
    Optional<BoardColumnEntity> findById(@Param("boardId") Long boardId, @Param("columnId") Long columnId);

}
