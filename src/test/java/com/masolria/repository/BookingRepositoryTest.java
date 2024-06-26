package com.masolria.repository;

import com.masolria.entity.Booking;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTest {
    private final BookingRepository bookingRepository=new BookingRepository();
    private Booking given;

    @BeforeEach
     void setupTestData() {
        given = Booking.builder()
                .isBooked(false)
                .bookedForUserId(1L)
                .bookingTimeEnd(LocalDateTime.of(2024, 5, 3, 11, 0))
                .bookingTimeStart(LocalDateTime.of(2024, 5, 3, 10, 0))
                .spaceId(1L)
                .build();
    }

    @Test

    void save() {
        Booking saved = bookingRepository.save(given);
        Assertions.assertEquals(given, saved);
    }

    @Test
    @DisplayName("Test if updated obj wasn't in repository")
    void update() {
        Booking updated = bookingRepository.update(given);
        assertThat(updated).isEqualTo(given);
    }

    @Test
    void delete() {
        //Booking saved = bookingRepository.save(given);
        bookingRepository.delete(given);
        Optional<Booking> optional = bookingRepository.findById(given.getId());
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("junit test finds Booking by id obj should be present")
    void findById() {
        bookingRepository.save(given);
        Optional<Booking> optional = bookingRepository.findById(given.getId());
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    @DisplayName("junit test finds list of bookings which should be not empty")
    void findAll() {
        bookingRepository.save(given);
       List<Booking> bookings=  bookingRepository.findAll();
        assertFalse(bookings.isEmpty());
    }
}