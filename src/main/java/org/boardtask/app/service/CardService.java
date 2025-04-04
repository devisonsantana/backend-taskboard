package org.boardtask.app.service;

import java.util.List;

import org.boardtask.app.dto.board.BoardDetailsResponseDTO;
import org.boardtask.app.dto.card.CardInsertRequestDTO;
import org.boardtask.app.dto.card.CardInsertResponseDTO;
import org.boardtask.app.dto.card.CardResponseDTO;
import org.boardtask.app.dto.column.BoardColumnDetailsResponseDTO;
import org.boardtask.app.entity.BoardColumnEntity;
import org.boardtask.app.entity.BoardColumnKinEnum;
import org.boardtask.app.entity.CardEntity;
import org.boardtask.app.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private BoardService boardService;

    public CardEntity insert(String username, Long boardId, CardInsertRequestDTO card) {
        var boardExists = boardService.findById(username, boardId);
        var col = getInitialColumn(boardExists);
        var columnEntity = new BoardColumnEntity(col.name(), col.order(), BoardColumnKinEnum.findByName(col.kind()));
        columnEntity.setId(col.id());
        var cardEntity = new CardEntity(card.title(), card.description());
        cardEntity.setBoardColumn(columnEntity);
        return repository.save(cardEntity);
    }

    public List<CardResponseDTO> findAll(String username, Long id) {
        var cards = repository.findAll().stream()
                .map(c -> new CardResponseDTO(c.getId(), c.getTitle(), c.getDescription())).toList();
        return cards;
    }

    private BoardColumnDetailsResponseDTO getInitialColumn(BoardDetailsResponseDTO boardDetails) {
        return boardDetails.columns()
                .stream()
                .filter(bc -> bc.kind().equals(BoardColumnKinEnum.TODO.name()))
                .findFirst().orElseThrow();
    }
}
