package com.masolria.service;

import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.repository.Jdbc.JdbcBookingRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The Booking service.
 */
@AllArgsConstructor
public class BookingService {
    /**
     * The Booking repository.
     */
    JdbcBookingRepository bookingRepository;
    /**
     * The Space service.
     */
    SpaceService spaceService;

    /**
     * Saves a new booking to the database.
     *
     * @param booking The booking to be saved.
     * @return The saved booking.
     */
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Updates an existing booking in the database.
     *
     * @param booking The updated booking.
     * @return The updated booking.
     */
    public Booking update(Booking booking) {
        return bookingRepository.update(booking);
    }

    /**
     * Deletes a booking from the database.
     *
     * @param booking The booking to be deleted.
     */
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    /**
     * Retrieves a booking from the database by its ID.
     *
     * @param id The ID of the booking to retrieve.
     * @return An Optional containing the booking if found, otherwise an empty Optional.
     */
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    /**
     * Retrieves all bookings from the database.
     *
     * @return A list of all bookings.
     */
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    /**
     * Retrieves all bookings for a specific date.
     *
     * @param localDate The date for which to retrieve bookings.
     * @return A list of bookings for the specified date.
     */
    public List<Booking> getByDate(LocalDate localDate){
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> b.getTimeStart()
                        .toLocalDate()
                        .equals(localDate))
                .toList();
    }

    /**
     * Retrieves all bookings for a specific space type.
     *
     * @param spaceType The type of space for which to retrieve bookings.
     * @return A list of bookings for the specified space type.
     */
    public List<Booking> getByType(SpaceType spaceType) {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> {
                    Optional<Space> optional = spaceService.findById(b.getSpaceId());
                if(optional.isEmpty()){return false;}
                    Space space = optional.get();
                    return space.getSpaceType().equals(spaceType);
                })
                .toList();
    }

    /**
     * Retrieves all bookings for a specific user.
     *
     * @param userId The ID of the user for which to retrieve bookings.
     * @return A list of bookings for the specified user.
     */
    public List<Booking> getByUserId(Long userId) {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> userId.equals(b.getForUserId()))
                .toList();

    }

    /**
     * Retrieves all free (unbooked) slots.
     *
     * @return A list of free booking slots.
     */
    public List<Booking> getFreeSlots() {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> !b.isBooked())
                .toList();
    }
}
