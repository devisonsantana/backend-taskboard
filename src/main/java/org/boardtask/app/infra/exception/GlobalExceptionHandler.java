package org.boardtask.app.infra.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> notFound() {
        var status = HttpStatus.NOT_FOUND;
        var message = "User not found";
        var time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var ex = new ExceptionDTO(status.value(), message, time);
        return ResponseEntity.status(status).body(ex);
    }
}
