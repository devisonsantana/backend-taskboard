package org.boardtask.app.service;

import java.util.ArrayList;
import java.util.List;

import org.boardtask.app.dto.board.BoardDetailsResponseDTO;
import org.boardtask.app.dto.board.BoardInsertRequestDTO;
import org.boardtask.app.dto.board.BoardInsertResponseDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.dto.card.CardResponseDTO;
import org.boardtask.app.dto.column.BoardColumnDetailsResponseDTO;
import org.boardtask.app.dto.column.BoardColumnInsertRequestDTO;
import org.boardtask.app.dto.column.BoardColumnInsertResponseDTO;
import org.boardtask.app.dto.column.BoardColumnResponseDTO;
import org.boardtask.app.entity.BoardColumnEntity;
import org.boardtask.app.entity.BoardColumnKinEnum;
import org.boardtask.app.entity.BoardEntity;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.infra.exception.handler.BoardColumnEntityNotFound;
import org.boardtask.app.infra.exception.handler.BoardEntityNotFoundException;
import org.boardtask.app.repository.BoardColumnRepository;
import org.boardtask.app.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardColumnRepository columnRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public BoardInsertResponseDTO insert(String username, BoardInsertRequestDTO board) {
        var userExists = userService.findByUsername(username);
        var userEntity = new UserEntity();
        userEntity.setId(userExists.id());
        userEntity.setUsername(userExists.username());
        var boardEntity = new BoardEntity();
        boardEntity.setName(board.name());
        boardEntity.setUser(userEntity);
        boardRepository.save(boardEntity);
        var columnsDto = insertColumn(boardEntity, board.column());
        var boardDto = new BoardInsertResponseDTO(userExists.id(), userExists.username(), boardEntity.getId(),
                boardEntity.getName(), columnsDto);
        return boardDto;
    }

    @Transactional
    private List<BoardColumnInsertResponseDTO> insertColumn(BoardEntity boardEntity,
            BoardColumnInsertRequestDTO boardColumn) {
        var columnNames = List.of(boardColumn.todo(), boardColumn.processing(), boardColumn.done(),
                boardColumn.canceled());
        var columns = createColumn(boardEntity, columnNames);
        return columnRepository.saveAll(columns)
                .stream()
                .map(c -> new BoardColumnInsertResponseDTO(c.getId(), c.getName()))
                .toList();
    }

    public BoardDetailsResponseDTO findById(String username, Long boardId) {
        var userId = userService.findByUsername(username).id();
        var board = boardRepository.findByUserIdAndBoardId(userId, boardId);
        if (board.isPresent()) {
            var entity = board.get();
            var col = entity
                    .getBoardColumns()
                    .stream()
                    .map(c -> {
                        var cards = c.getCard().stream()
                                .map(crd -> new CardResponseDTO(crd.getId(), crd.getTitle(), crd.getDescription()))
                                .toList();
                        return new BoardColumnDetailsResponseDTO(c.getId(),
                                c.getName(),
                                c.getOrder(),
                                c.getKind().name(), cards);
                    })
                    .toList();
            return new BoardDetailsResponseDTO(entity.getId(), entity.getName(), col);
        }
        throw new BoardEntityNotFoundException();
    }

    public BoardColumnDetailsResponseDTO findColumnById(String username, Long boardId, Long columnId) {
        var boardDb = findById(username, boardId);
        var optional = columnRepository.findById(boardDb.id(), columnId);
        if (optional.isPresent()) {
            var columnEntity = optional.get();
            var cards = columnEntity.getCard().stream()
                    .map(c -> new CardResponseDTO(c.getId(), c.getTitle(), c.getDescription())).toList();
            return new BoardColumnDetailsResponseDTO(columnEntity.getId(), columnEntity.getName(),
                    columnEntity.getOrder(), columnEntity.getKind().name(), cards);
        }
        throw new BoardColumnEntityNotFound();
    }

    public List<BoardResponseDTO> findAll(String username) {
        var userId = userService.findByUsername(username).id();
        var boardEntity = boardRepository.findAll(userId);
        var dto = boardEntity.stream().map(b -> {
            var col = b.getBoardColumns()
                    .stream()
                    .map(bc -> new BoardColumnResponseDTO(bc.getId(), bc.getName()))
                    .toList();
            var brd = new BoardResponseDTO(b.getId(), b.getName(), col);
            return brd;
        }).toList();
        return dto;
    }

    @Transactional
    @Modifying
    public void update(String username, Long boardId, String newName) {
        var userId = userService.findByUsername(username).id();
        var rowsAffected = boardRepository.updateById(newName, boardId, userId);
        if (rowsAffected == 0)
            throw new BoardEntityNotFoundException();
    }

    @Transactional
    public void deleteById(String username, Long boardId) {
        var userId = userService.findByUsername(username).id();
        if (!boardRepository.existsByUserIdAndBoardId(userId, boardId)) {
            throw new BoardEntityNotFoundException();
        }
        boardRepository.deleteById(boardId);
    }

    private List<BoardColumnEntity> createColumn(BoardEntity board, List<String> columnNames) {
        List<BoardColumnEntity> columns = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            var entity = new BoardColumnEntity(columnNames.get(i), i, BoardColumnKinEnum.values()[i]);
            entity.setBoard(board);
            columns.add(entity);
        }
        return columns;
    }
}
