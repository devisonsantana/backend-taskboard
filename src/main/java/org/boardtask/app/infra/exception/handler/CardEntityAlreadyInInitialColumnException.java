package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class CardEntityAlreadyInInitialColumnException extends PersistenceException {
    public CardEntityAlreadyInInitialColumnException() {
        super();
    }

    public CardEntityAlreadyInInitialColumnException(String message) {
        super(message);
    }
}
