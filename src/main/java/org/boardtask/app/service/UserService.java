package org.boardtask.app.service;

import java.util.List;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.infra.exception.handler.UserEntityAlreadyExistsException;
import org.boardtask.app.infra.exception.handler.UserEntityNotFoundException;
import org.boardtask.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserResponseDTO insert(UserRequestDTO user) {
        if (repository.existsByUsername(user.username())) {
            throw new UserEntityAlreadyExistsException(user.username());
        }
        var entity = new UserEntity(user.username(), encoder.encode(user.password()));
        entity = repository.save(entity);
        return new UserResponseDTO(entity.getId(), entity.getUsername());
    }

    public UserResponseDTO findById(Long id) {
        var optional = repository.findById(id);
        if (optional.isPresent()) {
            var dto = optional.get();
            return new UserResponseDTO(dto.getId(), dto.getUsername());
        }
        throw new UserEntityNotFoundException();
    }

    public UserResponseDTO findByUsername(String username) {
        var optional = repository.findByUsername(username);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new UserEntityNotFoundException(username);
    }

    public List<UserResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername()))
                .toList();
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new UserEntityNotFoundException();
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByUsername(String username) {
        if (!repository.existsByUsername(username)) {
            throw new UserEntityNotFoundException(username);
        }
        repository.deleteByUsername(username);
    }
}
