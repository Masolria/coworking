package com.masolria.service;

import com.masolria.Mapper.BookingMapper;
import com.masolria.annotation.Auditable;
import com.masolria.annotation.Loggable;
import com.masolria.dto.BookingDto;
import com.masolria.dto.SpaceDto;
import com.masolria.entity.Booking;
import com.masolria.entity.SpaceType;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.OccupiedConflictException;
import com.masolria.exception.ValidationException;
import com.masolria.repository.Jdbc.JdbcBookingRepository;
import com.masolria.validator.BookingValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The Booking service.
 */
@AllArgsConstructor
@Auditable
@Loggable
@Service
public class BookingService {
    /**
     * The Booking repository.
     */
    private final JdbcBookingRepository bookingRepository;
    /**
     * The Space service.
     */
    private final SpaceService spaceService;

    private final BookingMapper mapper;
    private static final BookingValidator validator = BookingValidator.getINSTANCE();

    /**
     * Saves a new booking to the database.
     *
     * @param booking The booking to be saved.
     * @return The saved booking.
     */
    public BookingDto save(BookingDto createDto) {
        if (validator.isValid(createDto)) {
            Booking booking = mapper.toEntity(createDto);
            Booking saved = bookingRepository.save(booking);
            return mapper.toDto(saved);
        }
        throw new ValidationException("Booking input dto is invalid.");
    }

    /**
     * Updates an existing booking in the database.
     *
     * @param booking The updated booking.
     * @return The updated booking.
     */
    public BookingDto update(BookingDto bookingDto) {
        if (validator.isValid(bookingDto)) {
            Booking booking = mapper.toEntity(bookingDto);
            Booking updated = bookingRepository.update(booking);
            return mapper.toDto(updated);
        }
        throw new ValidationException("Booking input dto is invalid.");
    }

    /**
     * Deletes a booking from the database.
     *
     * @param booking The booking to be deleted.
     */
    public void delete(Long id) throws EntityDeletionException {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            bookingRepository.delete(booking);
        } else throw new EntityDeletionException("Given dto doesn't match any row in the table.");
    }

    /**
     * Retrieves a booking from the database by its ID.
     *
     * @param id The ID of the booking to retrieve.
     * @return An Optional containing the booking if found, otherwise an empty Optional.
     */
    public BookingDto findById(Long id) throws EntityDeletionException {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            return mapper.toDto(booking);
        } else throw new EntityNotFoundException("Given id doesn't match any row in the table.");
    }

    /**
     * Retrieves all bookings from the database.
     *
     * @return A list of all bookings.
     */
    public List<BookingDto> findAll() {
        List<Booking> bookings = bookingRepository.findAll();
        return mapper.toDtoList(bookings);
    }

    /**
     * Retrieves all bookings for a specific date.
     *
     * @param localDate The date for which to retrieve bookings.
     * @return A list of bookings for the specified date.
     */
    public List<BookingDto> getByDate(LocalDate localDate) {
        List<BookingDto> bookings = findAll();
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
    public List<BookingDto> getByType(SpaceType spaceType) {
        List<BookingDto> bookings = findAll();
        return bookings.stream()
                .filter(b -> {
                    SpaceDto spaceDto = spaceService.findById(b.getSpaceId());
                    return spaceDto.getSpaceType().equals(spaceType.name());
                })
                .toList();
    }

    /**
     * Retrieves all bookings for a specific user.
     *
     * @param userId The ID of the user for which to retrieve bookings.
     * @return A list of bookings for the specified user.
     */
    public List<BookingDto> getByUserId(Long userId) {
        List<BookingDto> bookings = findAll();
        return bookings.stream()
                .filter(b -> userId.equals(b.getForUserId()))
                .toList();

    }

    /**
     * Retrieves all free (unbooked) slots.
     *
     * @return A list of free booking slots.
     */
    public List<BookingDto> getFreeSlots() {
        List<BookingDto> bookings = findAll();
        return bookings.stream()
                .filter(b -> !b.isBooked())
                .toList();
    }

    public void reserve(Long id) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            if (!booking.isBooked()) {
                booking.setBooked(true);
            } else throw new OccupiedConflictException("Booking slot is already reserved. Cannot perform action.");
        } else throw new EntityNotFoundException("Given id doesn't match any row in the table.");
    }

    public void release(Long id) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = optional.get();
            if (booking.isBooked()) {
                booking.setBooked(false);
            } else throw new OccupiedConflictException("Booking slot is already released. Cannot perform action.");
        } else throw new EntityNotFoundException("Given id doesn't match any row in the table.");
    }
}