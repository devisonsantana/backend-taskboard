package org.boardtask.app.infra.exception;

import java.time.Instant;

public record ExceptionDTO(int status, String message, Instant time) {

}
