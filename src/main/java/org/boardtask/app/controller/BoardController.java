package org.boardtask.app.controller;

import org.boardtask.app.dto.board.BoardRequestDTO;
import org.boardtask.app.dto.board.BoardResponseDTO;
import org.boardtask.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/{username}")
public class BoardController {
    @Autowired
    private BoardService service;

    @PostMapping("/board")
    public ResponseEntity<BoardResponseDTO> create(@PathVariable String username, @Valid BoardRequestDTO board,
            UriComponentsBuilder uriBuilder) {
        var dto = service.insert(username, board);
        var uri = uriBuilder.path("/{username}/{id}").buildAndExpand(username, dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
