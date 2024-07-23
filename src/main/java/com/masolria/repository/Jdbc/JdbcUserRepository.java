package com.masolria.repository.Jdbc;

import com.masolria.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.masolria.repository.Jdbc.Queries.*;

/**
 * The type Jdbc user repository.
 */
@Repository
@RequiredArgsConstructor
public class JdbcUserRepository {
    /**
     * The field configures the connection to the database
     */
    private final DriverManagerDataSource datasource;

    /**
     * Deletes a user from the table
     * if there is a row with the id of this user object.
     *
     * @param user the user for deletion
     */
    public void delete(User user) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USER_DELETE)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Finds user by email.
     *Returns optional with user if such row exist in the table. Otherwise, returns empty optional
     * @param email the email
     * @return the optional with user if exists.Otherwise, empty optional.
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM coworking_schema.users WHERE email = ?";
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(User.builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Finds user row in the table by id.
     * @param id the id
     * @return the user optional if such row exists int the table.
     *      * Otherwise, returns empty optional
     */
    public Optional<User> findById(Long id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USER_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(User.builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Find all user rows and maps them to corresponding objects.
     *
     * @return the list with all available users in the table.
     */
    public List<User> findAll() {
        try (Connection connection = datasource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(USER_FIND_ALL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                users.add(user);
            }
            resultSet.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Saves user to the table.
     *
     * @param user the user
     * @return the user with new id
     */
    public User save(User user) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USER_SAVE,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates user entry in the table.
     * If row in the table with id of given user doesn't exist, then doesn't update any row.
     * @param user the user
     * @return the user object the same, unchanged.
     */
    public User update(User user) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USER_UPDATE)) {
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