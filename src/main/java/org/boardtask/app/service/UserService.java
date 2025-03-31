package org.boardtask.app.service;

import org.boardtask.app.dto.user.UserRequestDTO;
import org.boardtask.app.entity.UserEntity;
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
    public UserEntity insert(UserRequestDTO request) {
        var entity = new UserEntity();
        entity.setUsername(request.username());
        entity.setPassword(encoder.encode(request.password()));
        return repository.save(entity);
    }

    public UserEntity findById(Long id){
        var optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
