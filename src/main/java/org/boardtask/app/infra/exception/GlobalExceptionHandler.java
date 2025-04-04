package org.boardtask.app.infra.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.boardtask.app.infra.exception.handler.BoardColumnEntityNotFound;
import org.boardtask.app.infra.exception.handler.BoardEntityNotFoundException;
import org.boardtask.app.infra.exception.handler.CardEntityAlreadyInFinalColumnException;
import org.boardtask.app.infra.exception.handler.CardEntityAlreadyInInitialColumnException;
import org.boardtask.app.infra.exception.handler.CardEntityNotFoundException;
import org.boardtask.app.infra.exception.handler.UserEntityAlreadyExistsException;
import org.boardtask.app.infra.exception.handler.UserEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserEntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> userEntityNotFound(UserEntityNotFoundException exception) {
        var status = HttpStatus.NOT_FOUND;
        var message = exception.getMessage() == null ? "User not found or does not exists"
                : "User %s does not exists".formatted(exception.getMessage());
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(UserEntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionDTO> userEntityAlreadyExists(UserEntityAlreadyExistsException exception) {
        var status = HttpStatus.CONFLICT;
        var message = "The username %s is already in use".formatted(exception.getMessage());
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(BoardEntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> boardEntityNotFound(BoardEntityNotFoundException exception) {
        var status = HttpStatus.NOT_FOUND;
        var message = exception.getMessage() == null ? "Board not found"
                : "Board %s not found".formatted(exception.getMessage());
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(BoardColumnEntityNotFound.class)
    public ResponseEntity<ExceptionDTO> boardColumnEntityNotFound() {
        var status = HttpStatus.NOT_FOUND;
        var message = "Column board not found";
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(CardEntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> cardEntityNotFound() {
        var status = HttpStatus.NOT_FOUND;
        var message = "Card not found";
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(CardEntityAlreadyInFinalColumnException.class)
    public ResponseEntity<ExceptionDTO> cardEntityAlreadyInFinalColumn() {
        var status = HttpStatus.BAD_REQUEST;
        var message = "This card is already in final column";
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }

    @ExceptionHandler(CardEntityAlreadyInInitialColumnException.class)
    public ResponseEntity<ExceptionDTO> cardEntityAlreadyInInitalColumn() {
        var status = HttpStatus.BAD_REQUEST;
        var message = "This card is already in initial column";
        var timeset = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), status.name(), message, timeset);
        return ResponseEntity.status(status).body(ex);
    }
}
