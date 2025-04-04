package org.boardtask.app.repository;

import java.util.List;
import java.util.Optional;

import org.boardtask.app.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query(value = "select * from board where user_id = :userId and id = :boardId", nativeQuery = true)
    Optional<BoardEntity> findByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);

    // Optional<BoardResponseDTO> findByUserIdAndBoardId(@Param("userId") Long
    // userId, @Param("boardId") Long boardId);
    @Query(value = "select * from board where user_id = :id", nativeQuery = true)
    List<BoardEntity> findAll(@Param("id") Long id);

    @Query("""
            select case when exists (
                select 1 from BoardEntity b where b.user.id = :userId and b.id = :boardId
            ) then
                true
            else
                false
            end
            """)
    boolean existsByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);

    @Modifying
    @Query(value = "update board set name = :newName where id = :boardId and user_id = :userId", nativeQuery = true)
    int updateById(@Param("newName") String newName, @Param("boardId") Long boardId, @Param("userId") Long userId);

}
