package com.masolria.repository;

import com.masolria.entity.Booking;


import java.time.LocalDateTime;
import java.util.*;

public class BookingRepository {
    private final Map<Long, Booking> bookings = new HashMap<>();
    private Long id = 1L;

    public BookingRepository() {
       Booking booking= Booking.builder()
                .spaceId(1L)
                .id(id++)
                .isBooked(false)
                .bookingTimeStart(LocalDateTime.of(2024, 6, 22, 13, 0))
                .bookingTimeEnd(LocalDateTime.of(2024, 6, 22, 14, 0))//.bookedForUserId(0L)
                .build();

       bookings.put(booking.getId(), booking);
        //при запуске приложения сгенерировать слоты свободные на сегодня и завтра, слоты с 8 утра до 8 вечера по часу(цикл)
    }

    public Booking save(Booking booking) {
        booking.setId(id++);
        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Booking update(Booking booking) {
        if (bookings.containsKey(booking.getId())) {
            bookings.put(booking.getId(), booking);
        } else {
            save(booking);
        }
        return booking;
    }

    public void delete(Booking booking) {
        bookings.remove(booking.getId());
    }

    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

}
