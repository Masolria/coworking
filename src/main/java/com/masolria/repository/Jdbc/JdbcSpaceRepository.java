package com.masolria.repository.Jdbc;

import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;

import com.masolria.db.ConnectionManager;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcSpaceRepository {

    private final ConnectionManager cManager;

    public Space save(Space space) {
        String sql = """
                INSERT INTO coworking_schema.space(location, space_type)
                VALUES(?,?::space_type);
                """;
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, space.getLocation());
            preparedStatement.setString(2, space.getSpaceType().name());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                space.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return space;
    }

    public Space update(Space space) {
        String sql = """
                UPDATE coworking_schema.space
                SET location = ?, space_type = ?::space_type
                WHERE id = ?
                """;
        PreparedStatement preparedStatement;
        try (Connection connection = cManager.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, space.getLocation());
            preparedStatement.setString(2, space.getSpaceType().name());
            preparedStatement.setLong(3, space.getId());
            preparedStatement.executeUpdate();
            return space;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return space;
    }

    public void delete(Space space) {
        try (Connection connection = cManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM coworking_schema.space WHERE id = ?")) {
            preparedStatement.setLong(1, space.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Optional<Space> findById(Long id) {
        String sql = "SELECT * FROM coworking_schema.space WHERE id = ?;";
        try(Connection connection = cManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
        preparedStatement.setLong(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return Optional.of(Space.builder()
                    .id(rs.getLong("id"))
                    .location(rs.getString("location"))
                    .spaceType(SpaceType.valueOf(rs.getString("space_type")))
                    .build());
        }} catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Space> findAll() {
        String sql = "SELECT * FROM coworking_schema.space;";
        try (Connection connection = cManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            List<Space> spaces = new ArrayList<>();
            while (rs.next()) {
                Space space = Space.builder()
                        .id(rs.getLong("id"))
                        .location(rs.getString("location"))
                        .spaceType(SpaceType.valueOf(rs.getString("space_type")))
                        .build();
                spaces.add(space);
            }
            return spaces;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}