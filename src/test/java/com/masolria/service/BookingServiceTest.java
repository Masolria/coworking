package com.masolria.service;

import com.masolria.Mapper.BookingMapper;
import com.masolria.Mapper.SpaceMapper;
import com.masolria.dto.BookingDto;
import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.repository.Jdbc.JdbcBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    JdbcBookingRepository bookingRepository;
    BookingMapper mapper = Mappers.getMapper(BookingMapper.class);
    SpaceMapper mapperSpace = Mappers.getMapper(SpaceMapper.class);
    @Mock
    SpaceService spaceService;

    BookingService bookingService;
    Booking given = Booking.builder()
            .id(3L)
            .isBooked(true)
            .timeStart(LocalDateTime.of(2024, 2, 12, 12, 0))
            .timeEnd(LocalDateTime.of(2024, 2, 12, 14, 0))
            .forUserId(3L)
            .spaceId(3L)
            .build();

    @BeforeEach
    void setupTestData() {
        bookingService = new BookingService(bookingRepository, spaceService, mapper);
    }

    @Test
    void save() {
        when(bookingRepository.save(given)).thenReturn(given);
        BookingDto givenDto = mapper.toDto(given);

        BookingDto saved = bookingService.save(givenDto);
        assertThat(saved).isEqualTo(givenDto);
    }

    @Test
    void getByUserId() {
        Long userId = 3L;
        List<Booking> allBookings = Arrays.asList(
                Booking.builder().id(1L).forUserId(3L).build(),
                Booking.builder().id(2L).forUserId(4L).build(),
                Booking.builder().id(3L).forUserId(3L).build()
        );

        when(bookingRepository.findAll()).thenReturn(allBookings);

        List<BookingDto> userBookings = bookingService.getByUserId(userId);
        assertThat(userBookings).hasSize(2);
    }

//    @Test
//    void update() {
//
//        BookingDto givenDto = mapper.toDto(given);
//        when(bookingRepository.update(given)).thenReturn(given);
//        BookingDto actual = bookingService.update(givenDto);
//        assertThat(actual).isEqualTo(givenDto);
//    }

    @Test
    void deleteThrows() {

        when(bookingRepository.findById(given.getId())).thenReturn(Optional.empty());
        assertThrows(EntityDeletionException.class,()->bookingService.delete(given.getId()));
    }
    @Test
    void delete() {
        Mockito.doNothing().when(bookingRepository).delete(any());
        when(bookingRepository.findById(given.getId())).thenReturn(Optional.of(given));
        bookingService.delete(given.getId());
        verify(bookingRepository).delete(given);

    }
    @Test
    @DisplayName("Test verifies that if booking doesnt exists,an exception is thrown.")
    void findById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.findById(1L));
    }

    @Test
    void findAll() {
        List<Booking> bookings = List.of(new Booking());
        List<BookingDto> expected = mapper.toDtoList(bookings);
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<BookingDto> actual = bookingService.findAll();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getByDate() {
        List<Booking> given = List.of(Booking.builder()
                .timeStart(LocalDateTime.of(2020, 1, 1, 12, 0))
                .build());
        List<BookingDto> expected = mapper.toDtoList(given);
        when(bookingRepository.findAll()).thenReturn(given);
        List<BookingDto> actual = bookingService.getByDate(LocalDate.of(2020, 1, 1));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getByType() {
        Space space = Space.builder().id(3L).spaceType(SpaceType.WORKING_SPACE).build();
        Booking booking = Booking.builder().spaceId(3L).build();
        List<Booking> given = List.of(booking);
        when(spaceService.findById(3L)).thenReturn(mapperSpace.toDto(space));
        when(bookingRepository.findAll()).thenReturn(given);
        List<BookingDto> expected = mapper.toDtoList(given);
        List<BookingDto> actual = bookingService.getByType(SpaceType.WORKING_SPACE);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void getFreeSlots() {
        Booking entity = Booking.builder()
                .isBooked(true)
                .id(3L)
                .timeStart(LocalDateTime.of(2024, 2, 15, 12, 0))
                .timeEnd(LocalDateTime.of(2024, 2, 15, 14, 0))
                .forUserId(3L)
                .spaceId(3L)
                .build();
        when(bookingRepository.findAll()).thenReturn(List.of(entity));
        List<BookingDto> bookings = bookingService.getFreeSlots();
        assertThat(bookings).hasSize(0);
    }
}