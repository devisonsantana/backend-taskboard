package org.boardtask.app.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.boardtask.app.entity.UserEntity;
import com.mysql.cj.jdbc.StatementImpl;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public UserEntity insert(UserEntity entity) {
        var sql = "INSERT INTO USER (NAME, PASSWORD) VALUES (?, ?)";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl)
                entity.setId(impl.getLastInsertID());
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return entity;
    }
}
