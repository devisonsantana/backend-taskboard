package org.boardtask.app.service;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserResponseDTO insert(UserRequestDTO user) {
        if (repository.findByUsername(user.username()).isPresent()) {
            throw new EntityExistsException();
        }
        var entity = new UserEntity(user.username(), encoder.encode(user.password()));
        entity = repository.save(entity);
        return new UserResponseDTO(entity.getId(), entity.getUsername());
    }

    @Transactional
    public void deleteByUsername(String username) {
        if (!repository.existsByUsername(username)) {
            throw new EntityNotFoundException();
        }
        repository.deleteByUsername(username);
    }

    public UserResponseDTO findById(Long id) {
        var optional = repository.findById(id);
        if (optional.isPresent()) {
            var dto = optional.get();
            return new UserResponseDTO(dto.getId(), dto.getUsername());
        }
        throw new EntityNotFoundException();
    }

    public UserResponseDTO findByUsername(String username) {
        var optional = repository.findByUsername(username);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException();
    }

}
