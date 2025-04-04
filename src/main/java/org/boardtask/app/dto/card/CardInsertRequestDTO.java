package org.boardtask.app.dto.card;

import jakarta.validation.constraints.NotBlank;

public record CardInsertRequestDTO(@NotBlank String title, @NotBlank String description) {

}
