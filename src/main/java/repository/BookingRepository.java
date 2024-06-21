package repository;

import entity.Booking;


import java.util.*;

public class BookingRepository {
    private final Map<Long, Booking> bookings = new HashMap<>();
    private Long id;
//    static {
//        //при запуске приложения сгенерировать слоты свободные на сегодня и завтра, слоты с 8 утра до 8 вечера по часу(цикл)
//    }

    public Booking save(Booking booking) {
        booking.setId(id++);
        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Booking update(Booking booking) {
        if (bookings.containsKey(booking.getId())){
            bookings.put(booking.getId(),booking);
        }else {
            booking.setId(id++);
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
