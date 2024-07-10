package com.masolria.Mapper;

import com.masolria.dto.BookingDto;
import com.masolria.entity.Booking;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookingMapper {
    @Mapping(source = "booked", target = "isBooked")
    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto bookingDto);
    List<BookingDto> toDtoList(List<Booking> bookings);
}
