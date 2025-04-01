package org.boardtask.app.controller;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/u/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        var user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/u/@{username}")
    public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable String username) {
        var user = service.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request,
            UriComponentsBuilder uriBuilder) {
        var user = service.insert(request);
        var uri = uriBuilder.path("/u/@{username}").buildAndExpand(user.username()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping("/u/@{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        service.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
