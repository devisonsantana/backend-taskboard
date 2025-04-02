package org.boardtask.app.infra.exception.handler;

import jakarta.persistence.PersistenceException;

public class UserEntityNotFoundException extends PersistenceException {
    public UserEntityNotFoundException() {
        super();
    }

    public UserEntityNotFoundException(String message) {
        super(message);
    }
}
