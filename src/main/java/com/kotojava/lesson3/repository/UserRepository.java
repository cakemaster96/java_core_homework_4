package com.kotojava.lesson3.repository;

import com.kotojava.lesson3.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    public UserRepository(DataSource pgUserDataSource) {
        this.dataSource = pgUserDataSource;
    }

    public void save(User newUser) {
        String sql = "INSERT INTO users (name, birthDate, city, address, email, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, newUser.getName());
            statement.setObject(2, newUser.getBirthDate());
            statement.setString(3, newUser.getCity());
            statement.setString(4, newUser.getAddress());
            statement.setString(5, newUser.getEmail());
            statement.setString(6, newUser.getPhone());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newUser.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public User getUserById(long id) {
        String sql = "SELECT id, name, birthDate, city, address, email, phone FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        String sql = "SELECT id, name, birthDate, city, address, email, phone FROM users ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapRow(resultSet));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .birthDate(resultSet.getObject("birthDate", LocalDate.class))
                .city(resultSet.getString("city"))
                .address(resultSet.getString("address"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .build();
    }
}