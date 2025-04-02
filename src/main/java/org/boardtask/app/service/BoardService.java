package org.boardtask.app.service;

import java.util.List;

import org.boardtask.app.dto.board.BoardRequestDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.entity.BoardEntity;
import org.boardtask.app.infra.exception.handler.BoardEntityNotFoundException;
import org.boardtask.app.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository repository;
    @Autowired
    private UserService userService;

    @Transactional
    public BoardResponseDTO insert(String username, BoardRequestDTO board) {
        var userDb = userService.findByUsername(username);
        var entity = new BoardEntity(board, userDb);
        entity = repository.save(entity);
        return new BoardResponseDTO(entity.getId(), entity.getName());
    }

    public BoardResponseDTO findById(String username, Long boardId) {
        var userId = userService.findByUsername(username).id();
        var optional = repository.findByUserIdAndBoardId(userId, boardId);
        if (optional.isPresent()) {
            var dto = optional.get();
            return dto;
        }
        throw new BoardEntityNotFoundException();
    }

    public List<BoardResponseDTO> findAll(String username) {
        var userId = userService.findByUsername(username).id();
        return repository.findAll(userId);
    }

    @Transactional
    public void deleteById(String username, Long boardId) {
        var userId = userService.findByUsername(username).id();
        if (!repository.existsByUserIdAndBoardId(userId, boardId)) {
            throw new BoardEntityNotFoundException();
        }
        repository.deleteById(boardId);
    }
}
