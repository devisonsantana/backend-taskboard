package org.boardtask.app.dto.card;

import jakarta.validation.constraints.NotBlank;

public record CardChangeTitleDescriptionRequestDTO(@NotBlank String title, @NotBlank String description) {

}
