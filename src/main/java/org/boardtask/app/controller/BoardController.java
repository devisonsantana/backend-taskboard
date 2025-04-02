package org.boardtask.app.controller;

import java.util.List;

import org.boardtask.app.dto.board.BoardRequestDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/{username}/board")
public class BoardController {
    @Autowired
    private BoardService service;

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> findById(@PathVariable String username, @PathVariable Long id) {
        var board = service.findById(username, id);
        return ResponseEntity.ok().body(board);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDTO>> showAll(@PathVariable String username) {
        var boards = service.findAll(username);
        return ResponseEntity.ok().body(boards);
    }

    @PostMapping
    public ResponseEntity<BoardResponseDTO> create(@PathVariable String username, @Valid BoardRequestDTO board,
            UriComponentsBuilder uriBuilder) {
        var dto = service.insert(username, board);
        var uri = uriBuilder.path("/{username}/{id}").buildAndExpand(username, dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String username, @PathVariable Long id) {
        service.deleteById(username, id);
        return ResponseEntity.noContent().build();
    }
}
