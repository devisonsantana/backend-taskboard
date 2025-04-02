package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class BoardEntityNotFoundException extends PersistenceException {
    public BoardEntityNotFoundException() {
        super();
    }

    public BoardEntityNotFoundException(String message) {
        super(message);
    }
}
