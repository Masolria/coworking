package com.masolria.controller;

import com.masolria.AppState;
import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.User;
import com.masolria.entity.enums.SpaceType;
import com.masolria.service.BookingService;
import com.masolria.service.EntryService;
import com.masolria.service.SpaceService;
import com.masolria.service.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class ConsoleController {
    private final UserService userService;
    private final SpaceService spaceService;
    private final EntryService entryService;
    private final BookingService bookingService;


    public void updateBooking(Long bookingId) {
    }

    public List<Booking> getAllFreeSlots() {
        return bookingService.getFreeSlots();
    }

    public List<Booking> getAllBookingByUser(User user) {
        return bookingService.getByUserId(user.getId());
    }

    public List<Booking> getAllBookingByType(SpaceType spaceType) {
        return bookingService.getByType(spaceType);
    }

    public List<Booking> getBookingByDate(LocalDate localDate) {
        return bookingService.getByDate(localDate);
    }

    public  AppState deleteBooking(Booking booking) {
        bookingService.delete(booking);
        return AppState.MENU;
    }
    public Optional<Booking> getBookingById(Long id) {
        return bookingService.findById(id);
    }

    public void deleteSpace(Space space) {
       spaceService.delete(space);
    }

    public List<Space> getAllSpaces() {
        return spaceService.getAll();
    }

    public void saveSpace(Space space) {
        spaceService.save(space);
    }



    //entryHandler
    public User authorize(String email, String password) {
        return entryService.authorize(email,password);
    }

    public User register(String email, String password) {

        return entryService.register(email,password);
    }

    public Optional<User> getUserByEmail(String input) {
       return userService.getByEmail(input);
    }

    public Optional<Space> getSpaceById(Long id) {
        return  spaceService.findById(id);
    }
}
