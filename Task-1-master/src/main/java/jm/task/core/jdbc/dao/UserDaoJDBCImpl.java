package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
        createUsersTable(); // Вызываем метод создания таблицы при инициализации DAO
    }

    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "age INT)";
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(dropTableSQL);
            System.out.println("Table dropped successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User " + name + " added successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String removeUserSQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUserSQL)) {

            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + id + " removed successfully");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAllUsersSQL = "SELECT id, name, lastName, age FROM users";
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsersSQL)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching users", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTableSQL = "TRUNCATE TABLE users RESTART IDENTITY";
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(cleanTableSQL);
            System.out.println("Table cleaned successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Error cleaning table", e);
        }
    }
}
