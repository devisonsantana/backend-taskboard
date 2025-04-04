package org.boardtask.app.dto.board;

import java.util.List;

import org.boardtask.app.dto.column.BoardColumnInsertResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BoardInsertResponseDTO(
                @JsonProperty("id") Long userId,
                @JsonProperty("username") String username,
                @JsonProperty("board_id") Long boardId,
                @JsonProperty("board_name") String boardName,
                @JsonProperty("columns") List<BoardColumnInsertResponseDTO> columns) {

}
