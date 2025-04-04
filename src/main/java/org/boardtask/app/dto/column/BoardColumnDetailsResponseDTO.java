package org.boardtask.app.dto.column;

import java.util.List;

import org.boardtask.app.dto.card.CardResponseDTO;

public record BoardColumnDetailsResponseDTO(
    Long id, 
    String name, 
    int order, 
    String kind,
    List<CardResponseDTO> cards) {

}
