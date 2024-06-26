package com.masolria.service;

import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.repository.BookingRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class BookingService {
    BookingRepository bookingRepository;
    SpaceService spaceService;

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking update(Booking booking) {
        return bookingRepository.update(booking);
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> getByDate(LocalDate localDate){
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> {
                    return b.getBookingTimeStart()
                            .toLocalDate()
                            .equals(localDate);
                })
                .toList();
    }

    public List<Booking> getByType(SpaceType spaceType) {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> {
                    Optional<Space> optional = spaceService.findById(b.getSpaceId());
                if(optional.isEmpty()){return false;}
                    Space space = optional.get();
                    return space.getSpaceType().equals(spaceType);
                })
                .toList();
    }

    public List<Booking> getByUserId(Long userId) {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> userId.equals(b.getBookedForUserId()))
                .toList();

    }

    public List<Booking> getFreeSlots() {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> !b.isBooked())
                .toList();
    }
}
