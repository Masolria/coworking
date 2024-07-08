package com.masolria.service;

import com.masolria.Mapper.BookingListMapper;
import com.masolria.Mapper.BookingMapper;
import com.masolria.annotations.Auditable;
import com.masolria.annotations.Loggable;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The Booking service.
 */
@AllArgsConstructor
@Auditable
@Loggable
public class BookingService {
    /**
     * The Booking repository.
     */
    JdbcBookingRepository bookingRepository;
    /**
     * The Space service.
     */
    SpaceService spaceService;

    BookingListMapper listMapper;
    BookingMapper mapper;
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
        throw new ValidationException();
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
        throw new ValidationException();
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
        } else throw new EntityDeletionException();
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
        } else throw new EntityNotFoundException();
    }

    /**
     * Retrieves all bookings from the database.
     *
     * @return A list of all bookings.
     */
    public List<BookingDto> findAll() {
        List<Booking> bookings = bookingRepository.findAll();
        return listMapper.toDtoList(bookings);
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
                .filter(b -> b.timeStart()
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
                    SpaceDto spaceDto = spaceService.findById(b.spaceId());
                    return spaceDto.spaceType().equals(spaceType.name());
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
                .filter(b -> userId.equals(b.forUserId()))
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
        if(optional.isPresent()){
           Booking booking = optional.get();
          if(! booking.isBooked()){
              booking.setBooked(true);
          }else throw new OccupiedConflictException();
        }else throw new EntityNotFoundException();
    }

    public void release(Long id) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if(optional.isPresent()){
            Booking booking = optional.get();
            if( booking.isBooked()){
                booking.setBooked(false);
            }else throw new OccupiedConflictException();
        }else throw new EntityNotFoundException();
    }
}
