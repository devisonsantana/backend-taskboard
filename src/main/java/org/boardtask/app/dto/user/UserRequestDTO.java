package org.boardtask.app.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Username field is mandatory") String username,
        @NotBlank(message = "Password field is mandatory") String password) {

}
