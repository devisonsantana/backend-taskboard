package org.boardtask.app.dto.board;

import java.util.List;

import org.boardtask.app.dto.column.BoardColumnDetailsResponseDTO;

public record BoardDetailsResponseDTO(Long id, String boardName, List<BoardColumnDetailsResponseDTO> columns) {

}
