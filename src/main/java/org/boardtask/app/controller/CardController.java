package org.boardtask.app.controller;

import java.util.List;

import org.boardtask.app.dto.card.CardChangeTitleDescriptionRequestDTO;
import org.boardtask.app.dto.card.CardInsertRequestDTO;
import org.boardtask.app.dto.card.CardInsertResponseDTO;
import org.boardtask.app.dto.card.CardResponseDTO;
import org.boardtask.app.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/u/{username}/board/{boardId}/column")
public class CardController {
    @Autowired
    private CardService service;

    @GetMapping("/{columnId}/card/{cardId}")
    public ResponseEntity<CardResponseDTO> findById(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long columnId, @PathVariable Long cardId) {
        var card = service.findById(username, boardId, columnId, cardId);
        return ResponseEntity.ok().body(card);
    }

    @GetMapping("{columnId}/card/list")
    public ResponseEntity<List<CardResponseDTO>> showAll(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long columnId) {
        var cards = service.findAll(username, boardId, columnId);
        return ResponseEntity.ok().body(cards);
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

    @PutMapping("/next/{cardId}")
    public ResponseEntity<Void> moveToNextColumn(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long cardId) {
        service.moveToNextColumn(username, boardId, cardId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/prev/{cardId}")
    public ResponseEntity<Void> moveToPreviousColumn(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long cardId) {
        service.moveToPreviousColumn(username, boardId, cardId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{columnId}/card/{cardId}")
    public ResponseEntity<Void> renameDescription(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long columnId, @PathVariable Long cardId,
            @RequestBody @Valid CardChangeTitleDescriptionRequestDTO newDescription) {
        // TODO: RENAME CARD TITLE OR DESCRIPTION
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{columnId}/card/{cardId}")
    public ResponseEntity<Void> delete(@PathVariable String username, @PathVariable Long boardId,
            @PathVariable Long columnId, @PathVariable Long cardId) {
        // TODO: DELETE CARD
        return ResponseEntity.noContent().build();
    }
}