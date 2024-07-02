package com.masolria.controller;

import com.masolria.AppState;
import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.entity.User;
import com.masolria.service.BookingService;
import com.masolria.service.EntryService;
import com.masolria.service.SpaceService;
import com.masolria.service.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The Console controller.
 */
@AllArgsConstructor
public class ConsoleController {
    private final UserService userService;
    private final SpaceService spaceService;
    private final EntryService entryService;
    private final BookingService bookingService;
    /**
     * Updates an existing booking.
     *
     * @param booking The booking to be updated.
     */
    public void updateBooking(Booking booking) {
        bookingService.update(booking);
    }

    /**
     * Retrieves all free booking slots.
     *
     * @return A list of free booking slots.
     */
    public List<Booking> getAllFreeSlots() {
        return bookingService.getFreeSlots();
    }

    /**
     * Retrieves all bookings associated with a specific user.
     *
     * @param user The user whose bookings to retrieve.
     * @return A list of bookings for the specified user.
     */
    public List<Booking> getAllBookingByUser(User user) {
        return bookingService.getByUserId(user.getId());
    }

    /**
     * Retrieves all bookings for a specific space type.
     *
     * @param spaceType The type of space for which to retrieve bookings.
     * @return A list of bookings for the specified space type.
     */
    public List<Booking> getAllBookingByType(SpaceType spaceType) {
        return bookingService.getByType(spaceType);
    }

    /**
     * Retrieves all bookings for a specific date.
     *
     * @param localDate The date for which to retrieve bookings.
     * @return A list of bookings for the specified date.
     */
    public List<Booking> getBookingByDate(LocalDate localDate) {
        return bookingService.getByDate(localDate);
    }

    /**
     * Deletes a booking and returns to the main menu.
     *
     * @param booking The booking to be deleted.
     * @return The next application state (AppState.MENU).
     */
    public AppState deleteBooking(Booking booking) {
        bookingService.delete(booking);
        return AppState.MENU;
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id The ID of the booking to retrieve.
     * @return An Optional containing the booking if found, otherwise an empty Optional.
     */
    public Optional<Booking> getBookingById(Long id) {
        return bookingService.findById(id);
    }

    /**
     * Deletes a space from the table.
     *
     * @param space The space to be deleted.
     */
    public void deleteSpace(Space space) {
        spaceService.delete(space);
    }

    /**
     * Retrieves all spaces from the table.
     *
     * @return List<Space> A list of all spaces.
     */
    public List<Space> getAllSpaces() {
        return spaceService.getAll();
    }

    /**
     * Saves a new space to the table.
     *
     * @param space The space to be saved.
     */
    public void saveSpace(Space space) {
        spaceService.save(space);
    }
    /**
     * Attempts to authorize a user based on their email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The authorized user if successful.
     */
    public User authorize(String email, String password) {
        return entryService.authorize(email, password);
    }

    /**
     * Registers a new user with the provided email and password.
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The newly registered user.
     */
    public User register(String email, String password) {

        return entryService.register(email, password);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param input The user's email address.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<User> getUserByEmail(String input) {
        return userService.getByEmail(input);
    }

    /**
     * Retrieves a space by its ID.
     * @param id The ID of the space to retrieve.
     * @return An Optional containing the space if found, otherwise an empty Optional.
     */
    public Optional<Space> getSpaceById(Long id) {
        return spaceService.findById(id);
    }

    /**
     * Retrieves all bookings from the table by user email address.
     *
     * @param email The user's email address.
     * @return A list of all booking by user.
     */
    public List<Booking> getAllBookingByUserEmail(String email) {
        Optional<User> optional = getUserByEmail(email);
        if (optional.isPresent()) {
           return getAllBookingByUser(optional.get());
        }
        else return Collections.emptyList();
    }
}