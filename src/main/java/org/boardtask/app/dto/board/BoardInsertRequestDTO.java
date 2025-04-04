package org.boardtask.app.dto.board;

import org.boardtask.app.dto.column.BoardColumnInsertRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardInsertRequestDTO(
        @NotBlank String name,
        @NotNull BoardColumnInsertRequestDTO column) {

}
