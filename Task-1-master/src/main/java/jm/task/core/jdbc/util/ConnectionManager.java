package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // Правильно настроенный URL
    private static final String USERNAME = "postgres"; // Имя пользователя PostgreSQL
    private static final String PASSWORD = "root"; // Пароль пользователя PostgreSQL

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
