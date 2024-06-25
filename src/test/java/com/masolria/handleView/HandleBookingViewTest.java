package com.masolria.handleView;

import com.masolria.AppContext;
import com.masolria.InputOutput.Input;
import com.masolria.InputOutput.Output;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.Booking;
import com.masolria.entity.User;
import com.masolria.entity.enums.SpaceType;
import com.masolria.exception.OccupiedConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandleBookingViewTest {
    @Mock
    private Input input;
    @Mock
    private Output output;
    @Mock
    private ConsoleController controller;

    @Test
    void showFreeSlots() {
        Booking b1 = new Booking();
       when(controller.getAllFreeSlots()).thenReturn(List.of(b1));
        HandleBookingView.showFreeSlots(output,controller);
        verify(output).output("There are all free slots");
        verify(output).output(b1.toString());
    }

    @Test
    void showByUser() {
        when(input.input()).thenReturn("m@mail.com");
        User user = new User();
        Booking b1 = new Booking();
        when(controller.getUserByEmail("m@mail.com")).thenReturn(Optional.of(user));
        when(controller.getAllBookingByUser(user)).thenReturn(List.of(b1));
        HandleBookingView.showByUser(input,output,controller);
        verify(output).output("Write the email of user whose bookings you would like to see");
        verify(output).output("There are all booking by given user");
        verify(output).output(b1.toString());
    }

    @Test
    @DisplayName("The test verifies that output for given type is correct")
    void showByType() {
        when(input.input()).thenReturn("1");//working space
        Booking b1 = Booking.builder().build();
        when(controller.getAllBookingByType(SpaceType.WorkingSpace))
                .thenReturn(List.of(b1));

        HandleBookingView.ShowByType(input, output, controller);
        verify(output).output("""
                Write the type of booking space you would like to see:
                1.working space
                2.conference hall
                """);
        verify(output).output("There are all booking by given type");
        verify(output).output(b1.toString());
    }

    @Test
    @DisplayName("The test verifies that output for given date is correct.input format yyyy mm dd e.g. 2024 01 01")
    void showByDate() {
        when(input.input()).thenReturn("2024 01 21");
        Booking b1 = Booking.builder().bookingTimeStart(LocalDateTime.of(2024, 1, 21, 12, 0)).build();

        when(controller.getBookingByDate(LocalDate.of(2024, 1, 21))).thenReturn(List.of(b1));
        HandleBookingView.ShowByDate(input, output, controller);

        verify(output).output("Write the date you what to see bookings, in the format yyyy mm dd");
        verify(output).output("There are all booking by given date");
        verify(output).output(b1.toString());
    }

    @Test
    @DisplayName("The test verifies that the slot is correctly reserved, verifies the expected result")
    void showReserve() {
        when(input.input()).thenReturn("1");

        when(controller.getBookingById(1L)).thenReturn(Optional.of(new Booking()));

        try (MockedStatic<AppContext> mockedStatic = Mockito.mockStatic(AppContext.class)) {
            User user = User.builder().id(3L).email("email@mock.com").password("password").build();
            mockedStatic.when(AppContext::getAuthorizedUser).thenReturn(user);
            assertThat(AppContext.getAuthorizedUser()).isEqualTo(user);
            HandleBookingView.showReserve(input, output, controller);
            verify(output).output("Write the id of the booking entry you want to keep for yourself");
            verify(output).output("You have successfully booked a space");
        }
    }

    @Test
    @DisplayName("The test checks that the exception is thrown if the slot is already booked")
    void showReserveThrows() {
        when(input.input()).thenReturn("1");
        Booking booking = Booking.builder().isBooked(true).build();
        when(controller.getBookingById(1L)).thenReturn(Optional.of(booking));

        try (MockedStatic<AppContext> mockedStatic = Mockito.mockStatic(AppContext.class)) {
            User user = User.builder().id(3L).email("email@mock.com").password("password").build();

            mockedStatic.when(AppContext::getAuthorizedUser)
                    .thenReturn(user);
            assertThat(AppContext.getAuthorizedUser()).isEqualTo(user);

            Assertions.assertThrows(OccupiedConflictException.class, () -> HandleBookingView.showReserve(input, output, controller));
            verify(output).output("Write the id of the booking entry you want to keep for yourself");
        }
    }

    @Test
    @DisplayName("The test verifies that a slot belonging to an authorized user has been properly released," +
                 "because the input slot id may not belong to them")
    void showReleaseBooking() {
        when(input.input()).thenReturn("1");
        Booking booking = Booking.builder().id(1L).bookedForUserId(3L).isBooked(true).build();
        when(controller.getBookingById(1L)).thenReturn(Optional.of(booking));

        try (MockedStatic<AppContext> mockedStatic = Mockito.mockStatic(AppContext.class)) {
            User user = User.builder().id(3L).email("email@mock.com").password("password").build();
            mockedStatic.when(AppContext::getAuthorizedUser)
                    .thenReturn(user);
            assertThat(AppContext.getAuthorizedUser()).isEqualTo(user);
            HandleBookingView.showReleaseBooking(input, output, controller);
            verify(output).output("Booking released successfully.");

        }
    }
}