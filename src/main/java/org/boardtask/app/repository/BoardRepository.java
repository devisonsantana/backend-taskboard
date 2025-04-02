package org.boardtask.app.repository;

import java.util.List;
import java.util.Optional;

import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query(value = "select id, name from board where user_id = :id", nativeQuery = true)
    List<BoardResponseDTO> findAll(@Param("id") Long id);

    @Query(value = "select id, name from board where user_id = :userId and id = :boardId", nativeQuery = true)
    Optional<BoardResponseDTO> findByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);

    boolean existsByIdAndUserId(Long boardId, Long userId);

}
