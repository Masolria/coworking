package com.masolria.repository.Jdbc;

import com.masolria.entity.Booking;
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
 * The Jdbc booking repository. Performs CRUD operations for booking entries to the database.
 * Postgresql dialect in all sql queries
 */
@Repository
@RequiredArgsConstructor
public class JdbcBookingRepository {
    /**
     * The field configures the connection to the database
     */
    private final DriverManagerDataSource datasource;

    /**
     * Deletes booking entry from table.
     * @param booking the booking for deletion
     */
    public void delete(Booking booking) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_DELETE)) {
            preparedStatement.setLong(1, booking.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds booking by id.
     * @param id the id
     * @return the optional of its booking.
     * Returns the empty optional if the booking entry doesn't exist in the table.
     */
    public Optional<Booking> findById(Long id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Booking.builder()
                            .id(rs.getLong("id"))
                            .isBooked(rs.getBoolean("is_booked"))
                            .spaceId(rs.getLong("space_id"))
                            .timeStart(rs.getTimestamp("time_start").toLocalDateTime())
                            .timeEnd(rs.getTimestamp("time_end").toLocalDateTime())
                            .forUserId(rs.getLong("for_user_id"))
                            .build()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    /**
     * Finds all booking entries and puts them in a list
     *
     * @return the list with all booking entries available in the table
     */
    public List<Booking> findAll() {
        try (Connection connection = datasource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(BOOKING_FIND_ALL);
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Booking booking = Booking.builder()
                        .id(rs.getLong("id"))
                        .isBooked(rs.getBoolean("is_booked"))
                        .spaceId(rs.getLong("space_id"))
                        .timeStart(rs.getTimestamp("time_start").toLocalDateTime())
                        .timeEnd(rs.getTimestamp("time_end").toLocalDateTime())
                        .forUserId(rs.getLong("for_user_id"))
                        .build();
                bookings.add(booking);
            }
            rs.close();
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Update booking. Retrieves id from the given booking and updates all fields in the table from it.
     * if booking entry doesn't exist, method doesn't update any row in the table
     * and returns given booking without any changes.
     * @param booking the booking for update
     * @return the booking
     */
    public Booking update(Booking booking) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_UPDATE)
        ) {
            preparedStatement.setBoolean(1, booking.isBooked());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(booking.getTimeStart()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(booking.getTimeEnd()));
            preparedStatement.setLong(4, booking.getSpaceId());
            preparedStatement.setLong(5, booking.getForUserId());
            preparedStatement.setLong(6, booking.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    /**
     * Save booking to the table
     *
     * @param booking the booking for saving
     * @return the booking with new id
     */
    public Booking save(Booking booking) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_SAVE, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setBoolean(1, booking.isBooked());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(booking.getTimeStart()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(booking.getTimeEnd()));
            preparedStatement.setLong(4, booking.getSpaceId());
            preparedStatement.setLong(5, booking.getForUserId());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setId(rs.getLong(1));
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
            return booking;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return booking;
    }
}