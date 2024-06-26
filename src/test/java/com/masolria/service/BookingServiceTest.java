package com.masolria.service;

import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.enums.SpaceType;
import com.masolria.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    SpaceService spaceService;
    @InjectMocks
    BookingService bookingService;
    Booking given = Booking.builder()
            .isBooked(false)
            .bookingTimeStart(LocalDateTime.of(2024, 2, 12, 12, 0))
            .bookedForUserId(3L)
            .build();

    @BeforeEach
    void setupTestData() {
    }

    @Test
    void save() {
        when(bookingRepository.save(given)).thenReturn(given);
        Booking saved = bookingService.save(given);
        assertThat(saved).isEqualTo(given);
    }

    @Test
    void getByUserId() {
        Long userId = 3L;
        List<Booking> allBookings = Arrays.asList(
                Booking.builder().id(1L).bookedForUserId(3L).build(),
                Booking.builder().id(2L).bookedForUserId(4L).build(),
                Booking.builder().id(3L).bookedForUserId(3L).build()
        );

        when(bookingRepository.findAll()).thenReturn(allBookings);

        List<Booking> userBookings = bookingService.getByUserId(userId);
        assertThat(userBookings).hasSize(2);
        assertThat(userBookings).allMatch(b -> userId.equals(b.getBookedForUserId()));
    }

    @Test
    void update() {
        Booking booking = Booking.builder().id(1L).build();
        when(bookingRepository.update(booking)).thenReturn(booking);
        Booking actual = bookingService.update(booking);
        assertThat(actual).isEqualTo(booking);
    }

    @Test
    void delete() {
        Booking booking = Booking.builder().id(1L).build();
        Mockito.doNothing().when(bookingRepository).delete(booking);
        bookingService.delete(booking);
        verify(bookingRepository).delete(booking);
    }

    @Test
    @DisplayName("Test verifies that if booking doesnt exists returns empty optional")
    void findById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Booking> optional = bookingService.findById(1L);
        assertThat(optional).isEmpty();
    }

    @Test
    void findAll() {
        List<Booking> bookings = List.of(new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<Booking> actual = bookingService.findAll();
        assertThat(actual).isEqualTo(bookings);

    }

    @Test
    void getByDate() {
        List<Booking> given = List.of(Booking.builder()
                .bookingTimeStart(LocalDateTime.of(2020, 1, 1, 12, 0))
                .build());
        when(bookingRepository.findAll()).thenReturn(given);
        List<Booking> actual = bookingService.getByDate(LocalDate.of(2020, 1, 1));
        assertThat(actual).isEqualTo(given);
    }

    @Test
    void getByType() {
        Space space = Space.builder().id(3L).spaceType(SpaceType.WorkingSpace).build();
        Booking booking = Booking.builder().spaceId(3L).build();
        List<Booking> given = List.of(booking);
        when(spaceService.findById(3L)).thenReturn(Optional.of(space));
        when(bookingRepository.findAll()).thenReturn(given);
        List<Booking> actual = bookingService.getByType(SpaceType.WorkingSpace);
        assertThat(actual).isEqualTo(given);
    }


    @Test
    void getFreeSlots() {
        Booking booking = Booking.builder().isBooked(true).build();
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        List<Booking> bookings = bookingService.getFreeSlots();
        assertThat(bookings).isEmpty();
    }
}