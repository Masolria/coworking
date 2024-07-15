package com.masolria.Mapper;

import com.masolria.dto.BookingDto;
import com.masolria.entity.Booking;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookingMapper {
    @Mapping(source = "booked", target = "booked")
    BookingDto toDto(Booking booking);
    @Mapping(source = "booked", target = "isBooked")
    Booking toEntity(BookingDto bookingDto);
    List<BookingDto> toDtoList(List<Booking> bookings);
}
