package org.boardtask.app.repository;

import java.util.Optional;

import org.boardtask.app.dto.user.UserResponseDTO;
import org.boardtask.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select u.id, u.username from user u where u.username = :username", nativeQuery = true)
    Optional<UserResponseDTO> findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    @Modifying
    @Query(value = "update user set username = :newUsername where username = :oldUsername", nativeQuery = true)
    int updateUsernameByUsername(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername);

    @Modifying
    @Query(value = "update user set username = :newUsername where id = :id", nativeQuery = true)
    int updateUsernameById(@Param("id") Long id, @Param("newUsername") String newUsername);
}
