package org.boardtask.app.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.boardtask.app.dao.UserDAO;
import org.boardtask.app.entity.UserEntity;

public class UserService {
    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public UserEntity insert(UserEntity entity) throws SQLException {
        try {
            new UserDAO(connection).insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
