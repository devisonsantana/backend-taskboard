package org.boardtask.app.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {
private ConnectionConfig(){
    super();
}
public static Connection getConnection() throws SQLException{
    String url = "jdbc:mysql://192.168.3.3/task_board_development";
    String user = "root";
    String password = "root";
    Connection connection = DriverManager.getConnection(url, user, password);
    connection.setAutoCommit(false);
    return connection;
}
}
