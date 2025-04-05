package org.boardtask.app.service;

import java.util.List;

import org.boardtask.app.dto.board.BoardDetailsResponseDTO;
import org.boardtask.app.dto.card.CardInsertRequestDTO;
import org.boardtask.app.dto.card.CardResponseDTO;
import org.boardtask.app.dto.column.BoardColumnDetailsResponseDTO;
import org.boardtask.app.entity.BoardColumnEntity;
import org.boardtask.app.entity.BoardColumnKinEnum;
import org.boardtask.app.entity.CardEntity;
import org.boardtask.app.infra.exception.handler.CardEntityAlreadyInFinalColumnException;
import org.boardtask.app.infra.exception.handler.CardEntityAlreadyInInitialColumnException;
import org.boardtask.app.infra.exception.handler.CardEntityNotFoundException;
import org.boardtask.app.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CardResponseDTO findById(String username, Long boardId, Long columnId, Long cardId) {
        var columnExists = boardService.findColumnById(username, boardId, columnId);
        var optional = repository.findById(columnExists.id(), cardId);
        if (optional.isPresent()) {
            var entity = optional.get();
            return new CardResponseDTO(entity.getId(), entity.getTitle(), entity.getDescription());
        }
        throw new CardEntityNotFoundException();
    }

    public List<CardResponseDTO> findAll(String username, Long boardId, Long columnId) {
        var columnExists = boardService.findColumnById(username, boardId, columnId).id();
        var cards = repository.findAll(columnExists).stream()
                .map(c -> new CardResponseDTO(c.getId(), c.getTitle(), c.getDescription())).toList();
        return cards;
    }

    @Transactional
    @Modifying
    public void moveToNextColumn(String username, Long boardId, Long cardId) {
        var columns = boardService.findById(username, boardId).columns();
        var optional = repository.findById(cardId);
        if (!optional.isPresent()) {
            throw new CardEntityNotFoundException();
        }
        var columnId = optional.get().getBoardColumn().getId();
        var currentColumn = columns.stream()
                .filter(bc -> bc.id().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new CardEntityNotFoundException());
        if (currentColumn.kind().equals(BoardColumnKinEnum.DONE.name())) {
            throw new CardEntityAlreadyInFinalColumnException();
        }
        var nextColumn = columns.stream()
                .filter(bc -> bc.order() == currentColumn.order() + 1).findFirst().orElseThrow();
        repository.updateToColumn(nextColumn.id(), cardId);
    }

    @Transactional
    @Modifying
    public void moveToPreviousColumn(String username, Long boardId, Long cardId) {
        var columns = boardService.findById(username, boardId).columns();
        var optional = repository.findById(cardId);
        if (!optional.isPresent()) {
            throw new CardEntityNotFoundException();
        }
        var columnId = optional.get().getBoardColumn().getId();
        var currentColumn = columns.stream()
                .filter(bc -> bc.id().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new CardEntityNotFoundException());
        if (currentColumn.kind().equals(BoardColumnKinEnum.TODO.name())) {
            throw new CardEntityAlreadyInInitialColumnException();
        }
        var previousColumn = columns.stream()
                .filter(bc -> bc.order() == currentColumn.order() - 1).findFirst().orElseThrow();
        repository.updateToColumn(previousColumn.id(), cardId);
    }

    private BoardColumnDetailsResponseDTO getInitialColumn(BoardDetailsResponseDTO boardDetails) {
        return boardDetails.columns()
                .stream()
                .filter(bc -> bc.kind().equals(BoardColumnKinEnum.TODO.name()))
                .findFirst().orElseThrow();
    }
}
