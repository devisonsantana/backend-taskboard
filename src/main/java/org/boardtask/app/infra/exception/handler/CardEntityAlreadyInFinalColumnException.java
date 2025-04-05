package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class CardEntityAlreadyInFinalColumnException extends PersistenceException {
    public CardEntityAlreadyInFinalColumnException() {
        super();
    }

    public CardEntityAlreadyInFinalColumnException(String message) {
        super(message);
    }
}
