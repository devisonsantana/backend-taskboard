package org.boardtask.app.infra.exception;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExceptionDTO(int code, String status, String message, @JsonProperty("timestamp") Instant times) {

}
