package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class CardEntityNotFoundException extends PersistenceException {
    public CardEntityNotFoundException() {
        super();
    }

    public CardEntityNotFoundException(String message) {
        super(message);
    }
}
