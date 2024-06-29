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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleControllerTest {
    @InjectMocks
    private ConsoleController controller;
    @Mock
    private UserService userService;
    @Mock
    private SpaceService spaceService;
    @Mock
    private EntryService entryService;
    @Mock
    private BookingService bookingService;
    @Test
    void updateBooking_CallsBookingServiceUpdate() {
        Booking booking = new Booking();
        controller.updateBooking(booking);
       verify(bookingService).update(booking);
    }
    @Test
    void getAllFreeSlots_ReturnsBookingServiceFreeSlots() {
        List<Booking> expected = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getFreeSlots()).thenReturn(expected);
        List<Booking> result = controller.getAllFreeSlots();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getAllBookingByUser_ReturnsBookingServiceByUserId() {
        User user = new User();
        List<Booking> expected = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getByUserId(user.getId())).thenReturn(expected);
        List<Booking> result = controller.getAllBookingByUser(user);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getAllBookingByType_ReturnsBookingServiceByType() {
        SpaceType spaceType = SpaceType.CONFERENCE_HALL;
        List<Booking> expected = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getByType(spaceType)).thenReturn(expected);
        List<Booking> result = controller.getAllBookingByType(spaceType);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getBookingByDate_ReturnsBookingServiceByDate() {
        LocalDate date = LocalDate.now();
        List<Booking> expected = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getByDate(date)).thenReturn(expected);
        List<Booking> result = controller.getBookingByDate(date);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void deleteBooking_CallsBookingServiceDeleteAndReturnsAppState() {
        Booking booking = new Booking();
        AppState expected = AppState.MENU;
        AppState result = controller.deleteBooking(booking);
        verify(bookingService).delete(booking);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getBookingById_ReturnsBookingServiceById() {
        Long id = 1L;
        Optional<Booking> expected = Optional.of(new Booking());
        when(bookingService.findById(id)).thenReturn(expected);
        Optional<Booking> result = controller.getBookingById(id);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void deleteSpace_CallsSpaceServiceDelete() {
        Space space = new Space();
        controller.deleteSpace(space);
        verify(spaceService).delete(space);
    }

    @Test
    void saveSpace_CallsSpaceServiceSave() {
        Space space = new Space();
        controller.saveSpace(space);
        verify(spaceService).save(space);
    }

    @Test
    void authorize_ReturnsEntryServiceAuthorize() {
        String email = "test@example.com";
        String password = "password";
        User expected = new User();
        when(entryService.authorize(email, password)).thenReturn(expected);
        User result = controller.authorize(email, password);
        Assertions.assertEquals(expected, result);
    }
    @Test
    void register_ReturnsEntryServiceRegister() {
        String email = "test@example.com";
        String password = "password";
        User expected = new User();
        when(entryService.register(email, password)).thenReturn(expected);

        User result = controller.register(email, password);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void getUserByEmail_ReturnsUserServiceGetByEmail() {
        String email = "test@example.com";
        Optional<User> expected = Optional.of(new User());
       when(userService.getByEmail(email)).thenReturn(expected);

        Optional<User> result = controller.getUserByEmail(email);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void getSpaceById_ReturnsSpaceServiceFindById() {
        Long id = 1L;
        Optional<Space> expected = Optional.of(new Space());
        when(spaceService.findById(id)).thenReturn(expected);

        Optional<Space> result = controller.getSpaceById(id);

        Assertions.assertEquals(expected, result);
    }
}
