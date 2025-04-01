package org.boardtask.app.service;

import org.boardtask.app.dto.board.BoardRequestDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.entity.BoardEntity;
import org.boardtask.app.entity.UserEntity;
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
}
