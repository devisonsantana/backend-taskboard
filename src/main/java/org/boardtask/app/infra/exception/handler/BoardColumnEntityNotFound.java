package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class BoardColumnEntityNotFound extends PersistenceException {
    public BoardColumnEntityNotFound() {
        super();
    }

    BoardColumnEntityNotFound(String message) {
        super(message);
    }
}
