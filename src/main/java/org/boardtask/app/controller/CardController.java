package org.boardtask.app.controller;

import java.util.List;

import org.boardtask.app.dto.card.CardInsertRequestDTO;
import org.boardtask.app.dto.card.CardInsertResponseDTO;
import org.boardtask.app.dto.card.CardResponseDTO;
import org.boardtask.app.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/u/{username}/board/{boardId}/column")
public class CardController {
    @Autowired
    private CardService service;

    @GetMapping("/card/list")
    public ResponseEntity<List<CardResponseDTO>> showAll(@PathVariable String username, @PathVariable Long boardId) {
        var cards = service.findAll(username, boardId);
        return null;
    }

    @PostMapping("/card")
    public ResponseEntity<CardInsertResponseDTO> create(@PathVariable String username, @PathVariable Long boardId,
            CardInsertRequestDTO card, UriComponentsBuilder uriBuilder) {
        var cardEntity = service.insert(username, boardId, card);
        var cardDto = new CardInsertResponseDTO(cardEntity.getId(), cardEntity.getTitle(), cardEntity.getDescription());
        var uri = uriBuilder.path("/u/{username}/board/{board}/column/{columnId}/card/{cardId}")
                .buildAndExpand(username, boardId, cardEntity.getBoardColumn().getId(), cardDto.id()).toUri();
        return ResponseEntity.created(uri).body(cardDto);
    }
}
