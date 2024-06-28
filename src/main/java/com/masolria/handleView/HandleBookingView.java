package com.masolria.handleView;

import com.masolria.AppContext;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.Booking;
import com.masolria.entity.SpaceType;
import com.masolria.entity.User;
import com.masolria.exception.OccupiedConflictException;
import com.masolria.in.Input;
import com.masolria.out.Output;
import com.masolria.util.DateTimeParseUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HandleBookingView {

    public static void showFreeSlots(Output output, ConsoleController controller) {
        output.output("There are all free slots");
        List<Booking> bookings = controller.getAllFreeSlots();
        for (Booking booking : bookings) {
            output.output(booking.toString());
        }

    }

    //User
    public static void showByUser(Input input, Output output, ConsoleController controller) {
        output.output("Write the email of user whose bookings you would like to see");
        Optional<User> optional = controller.getUserByEmail(input.input());
        if (optional.isPresent()) {
            List<Booking> bookings = controller.getAllBookingByUser(optional.get());
            output.output("There are all booking by given user");
            for (Booking booking : bookings) {
                output.output(booking.toString());
            }
        } else {
            output.output("There are no such user with given email");
        }

    }

    public static void ShowByType(Input input, Output output, ConsoleController controller) {
        output.output("""
                      Write the type of booking space you would like to see:
                      1.working space
                      2.conference hall
                      """);
        Long spaceNum = Long.valueOf(input.input());
        SpaceType spaceType;
        if(spaceNum.equals(1L)){
            spaceType = SpaceType.WORKING_SPACE;
        } else if (spaceNum.equals(2L)) {
            spaceType = SpaceType.CONFERENCE_HALL;
        }else{
            output.output("Your space type input is not correct");
            return;
        }

        List<Booking> bookings = controller.getAllBookingByType(spaceType);
        output.output("There are all booking by given type");
        for (Booking booking : bookings) {
            output.output(booking.toString());
        }
    }

    public static void ShowByDate(Input input, Output output, ConsoleController controller) {
        output.output("Write the date you what to see bookings, in the format yyyy mm dd");
        String format = input.input();
        try {
            LocalDate localDate = DateTimeParseUtil.parseToLDate(format);
            List<Booking> bookings = controller.getBookingByDate(localDate);
            output.output("There are all booking by given date");
            for (Booking booking : bookings) {
                output.output(booking.toString());
            }
        } catch (DateTimeException e) {
            System.out.println("Your date input is not correct");
        }
    }

    public static void showReserve(Input input, Output output, ConsoleController controller) {
        output.output("Write the id of the booking entry you want to keep for yourself");
        showFreeSlots(output, controller);
        try {
            Long bookingId = Long.parseLong(input.input());
            Optional<Booking> optionalBooking = controller.getBookingById(bookingId);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();
                if (booking.isBooked()) {
                    throw new OccupiedConflictException("Sorry, this slot is already booked");
                }
                booking.setBooked(true);
                booking.setForUserId(AppContext.getAuthorizedUser().getId());
                controller.updateBooking(booking);
                output.output("You have successfully booked a space");
                return;
            }
            output.output("Sorry, slot with given id doesn't exist");
        } catch (NumberFormatException e) {
            output.output("Your input isn't correct");
        }
    }

    public static void showReleaseBooking(Input input, Output output, ConsoleController controller) {
        User user = AppContext.getAuthorizedUser();
        List<Booking> bookings = controller.getAllBookingByUser(user);
        output.output("There are all your bookings, write id you want to release");
        for (Booking booking : bookings) {
            output.output(booking.toString());
        }
        try {
            Long id = Long.parseLong(input.input());
            Optional<Booking> bookingOptional = controller.getBookingById(id);
            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                if (booking.getForUserId().equals(user.getId())) {
                    booking.setBooked(false);
                    booking.setForUserId(null);
                    controller.updateBooking(booking);
                    output.output("Booking released successfully.");
                } else {
                    output.output("You can only release your own bookings");
                }
            } else {
                output.output("There is no such booking with the given id");
            }
        } catch (NumberFormatException e) {
            output.output("Your input isn't correct");
        } catch (RuntimeException e) {
            output.output("Unrecognized exception here");
        }
    }
}