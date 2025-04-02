package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class UserEntityAlreadyExistsException extends PersistenceException {
    public UserEntityAlreadyExistsException() {
        super();
    }

    public UserEntityAlreadyExistsException(String message) {
        super(message);
    }
}
