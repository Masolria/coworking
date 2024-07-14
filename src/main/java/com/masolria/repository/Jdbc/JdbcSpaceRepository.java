package com.masolria.repository.Jdbc;

import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
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
 * The Jdbc space repository.Performs CRUD operations for space entries to the database.
 * Postgresql dialect in all sql queries.
 */
@Repository
@RequiredArgsConstructor
public class JdbcSpaceRepository {
    /**
     * The field configures the connection to the database
     */
    private final DriverManagerDataSource datasource;

    /**
     * Saves space to the table. Assigns the generated value to id
     *
     * @param space the space for saving
     * @return the space with new id
     */
    public Space save(Space space) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SPACE_INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, space.getLocation());
            preparedStatement.setString(2, space.getSpaceType().name());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    space.setId(rs.getLong(1));
                } else {
                    rs.close();
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return space;
    }

    /**
     * Updates space entry in the table.
     * If row in the table with id of given space doesn't exist, then doesn't update any row.
     *
     * @param space the space
     * @return the space object the same, unchanged.
     */
    public Space update(Space space) {
        PreparedStatement preparedStatement;
        try (Connection connection = datasource.getConnection()) {
            preparedStatement = connection.prepareStatement(SPACE_UPDATE);
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

    /**
     * Deletes space row from the table if exists. Otherwise, doesn't delete any.
     *
     * @param space the space for deletion
     */
    public void delete(Space space) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SPACE_DELETE)) {
            preparedStatement.setLong(1, space.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Finds row by space id and maps to the corresponding obj.
     *
     * @param id the id
     * @return Optional object with a space if found. Otherwise, return empty optional.
     */
    public Optional<Space> findById(Long id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SPACE_GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {

                    return Optional.of(Space.builder()
                            .id(rs.getLong("id"))
                            .location(rs.getString("location"))
                            .spaceType(SpaceType.valueOf(rs.getString("space_type")))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Find all records in the table and puts them in the list.
     *
     * @return the list with all available space entries.
     */
    public List<Space> findAll() {
        try (Connection connection = datasource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SPACE_FIND_ALL);
            List<Space> spaces = new ArrayList<>();
            while (rs.next()) {
                Space space = Space.builder()
                        .id(rs.getLong("id"))
                        .location(rs.getString("location"))
                        .spaceType(SpaceType.valueOf(rs.getString("space_type")))
                        .build();
                spaces.add(space);
            }
            rs.close();
            return spaces;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}