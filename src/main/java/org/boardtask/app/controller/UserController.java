package org.boardtask.app.controller;

import java.util.List;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/uid/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        var user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/u/{username}")
    public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable String username) {
        var user = service.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/u/all")
    public ResponseEntity<List<UserResponseDTO>> showAll() {
        var users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request,
            UriComponentsBuilder uriBuilder) {
        var user = service.insert(request);
        var uri = uriBuilder.path("/u/{username}").buildAndExpand(user.username()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/uid/{id}/{newUsername}")
    public ResponseEntity<Void> renameById(@PathVariable Long id, @PathVariable String newUsername) {
        service.updateUsername(id, newUsername);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/u/{oldUsername}/{newUsername}")
    public ResponseEntity<Void> renameByUsername(@PathVariable String oldUsername, @PathVariable String newUsername) {
        service.updateUsername(oldUsername, newUsername);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/uid/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/u/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        service.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
