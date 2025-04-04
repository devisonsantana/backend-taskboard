package org.boardtask.app.dto.board;

import java.util.List;

import org.boardtask.app.dto.column.BoardColumnResponseDTO;

public record BoardResponseDTO(Long id, String name, List<BoardColumnResponseDTO> columns) {

}
