package org.boardtask.app.controller;

import java.util.List;

import org.boardtask.app.dto.board.BoardDetailsResponseDTO;
import org.boardtask.app.dto.board.BoardInsertRequestDTO;
import org.boardtask.app.dto.board.BoardInsertResponseDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.dto.column.BoardColumnDetailsResponseDTO;
import org.boardtask.app.service.BoardService;
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

@RestController
@RequestMapping("/u/{username}/board")
public class BoardController {
    @Autowired
    private BoardService service;

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailsResponseDTO> findById(@PathVariable String username, @PathVariable Long id) {
        var board = service.findById(username, id);
        return ResponseEntity.ok().body(board);
    }

    @GetMapping("/{boardId}/column/{columnId}")
    public ResponseEntity<BoardColumnDetailsResponseDTO> findColumnById(@PathVariable("username") String username,
            @PathVariable("boardId") Long boarId, @PathVariable("columnId") Long columnId) {
        var board = service.findColumnById(username, boarId, columnId);
        return ResponseEntity.ok().body(board);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDTO>> showAll(@PathVariable String username) {
        var boards = service.findAll(username);
        return ResponseEntity.ok().body(boards);
    }

    @PostMapping
    public ResponseEntity<BoardInsertResponseDTO> create(@PathVariable String username,
            @RequestBody BoardInsertRequestDTO entity,
            UriComponentsBuilder builder) {
        var board = service.insert(username, entity);
        var uri = builder.path("/u/{username}/board/{id}").buildAndExpand(username, board.boardId()).toUri();
        return ResponseEntity.created(uri).body(board);
    }

    @PutMapping("/{id}/{newName}")
    public ResponseEntity<Void> rename(@PathVariable String username, @PathVariable Long id,
            @PathVariable String newName) {
        service.update(username, id, newName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String username, @PathVariable Long id) {
        service.deleteById(username, id);
        return ResponseEntity.noContent().build();
    }
}
