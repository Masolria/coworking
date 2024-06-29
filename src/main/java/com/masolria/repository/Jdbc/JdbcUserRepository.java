package com.masolria.repository.Jdbc;

import com.masolria.entity.User;
import com.masolria.db.ConnectionManager;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserRepository {

    private final ConnectionManager cManager;

    public void delete(User user) {
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM coworking_schema.users WHERE id = ?")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM coworking_schema.users WHERE email = ?";
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(User.builder()
                        .id(rs.getLong("id"))
                        .email("email")
                        .password("password")
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM coworking_schema.users WHERE id = ?;";
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(User.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM coworking_schema.users;";
        try (Connection connection = cManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public User save(User user) {
        String sql = "INSERT INTO coworking_schema.users(email,password) VALUES (?, ?)";
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User update(User user) {
        String sql = """
                UPDATE coworking_schema.users
                SET email = ?, password = ?
                WHERE id = ?
                """;
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3,user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}