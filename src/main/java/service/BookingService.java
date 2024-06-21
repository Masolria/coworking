package service;

import entity.Booking;
import entity.Space;
import entity.User;
import entity.enums.SpaceType;
import repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                    LocalDate currentLocalDate = b.getBookingTimeStart().toLocalDate();
                    return b.getBookingTimeStart()
                            .toLocalDate()
                            .equals(localDate);
                })
                .toList();
    }

    public List<Booking> getByType(SpaceType spaceType) {
        //user.getId();
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> {
                    Optional<Space> optional = spaceService.findById(b.getSpaceId());
                if(optional.isEmpty()){return false;}
                    Space space = optional.get();
                    if (space.getSpaceType().equals(spaceType)){
                        return  true;
                    }
                    return false;
                })
                .toList();
    }

    public List<Booking> getByUser(User user) {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> user.getId().equals(b.getBookedForUserId()))
                .toList();

    }

    public List<Booking> getFreeSlots() {
        List<Booking> bookings = findAll();
        return bookings.stream()
                .filter(b -> !b.isBooked())
                .toList();
    }
}
