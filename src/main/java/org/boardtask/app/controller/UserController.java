package org.boardtask.app.controller;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> create(@RequestBody @Valid UserRequestDTO request, UriComponentsBuilder uriBuilder) {
        var user = service.insert(request);
        var uri = uriBuilder.path("/u/@{username}").buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("/u/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        var user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/u/@{username}")
    public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable String username){
        var user = service.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}
