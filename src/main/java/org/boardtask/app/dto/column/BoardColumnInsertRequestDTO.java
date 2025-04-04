package org.boardtask.app.dto.column;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record BoardColumnInsertRequestDTO(
        @JsonProperty("todo_column_name") @NotBlank String todo,
        @JsonProperty("in_process_column_name") @NotBlank String processing,
        @JsonProperty("done_column_name") @NotBlank String done,
        @JsonProperty("canceled_column_name") @NotBlank String canceled) {

}
