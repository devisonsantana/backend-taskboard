package org.boardtask.app.repository;

import java.util.List;
import java.util.Optional;

import org.boardtask.app.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query(value = "select * from card where id = :cardId and board_column_id = :columnId", nativeQuery = true)
    Optional<CardEntity> findById(@Param("columnId") Long columnId, @Param("cardId") Long cardId);

    @Query(value = "select * from card where board_column_id = :columnId", nativeQuery = true)
    List<CardEntity> findAll(@Param("columnId") Long columnId);

    @Modifying
    @Query(value = "update card set board_column_id = :columnId where id = :cardId", nativeQuery = true)
    int updateToColumn(@Param("columnId") Long columnId, @Param("cardId") Long cardId);

}
