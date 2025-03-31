package org.boardtask.app.controller;

import java.sql.SQLException;

import org.boardtask.app.dbconnect.ConnectionConfig;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/sign-in")
public class UserController {
    private UserService service;

    private UserController() throws SQLException {
        service = new UserService(ConnectionConfig.getConnection());
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity entity, UriComponentsBuilder uriBuilder)
            throws SQLException {
        var dto = service.insert(entity);
        var uri = uriBuilder.path("/u/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }
}
