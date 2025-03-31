package org.boardtask.app.service;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.entity.UserEntity;
import org.boardtask.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserEntity insert(UserRequestDTO user) {
        var entity = new UserEntity(user.username(), encoder.encode(user.password()));
        return repository.save(entity);
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
            var dto = optional.get();
            return new UserResponseDTO(dto.getId(), dto.getUsername());
        }
        throw new EntityNotFoundException();
    }

}
