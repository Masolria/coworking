package com.masolria.Mapper;

import com.masolria.dto.BookingDto;
import com.masolria.entity.Booking;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = BookingMapper.class)
public interface BookingListMapper {
    List<BookingDto> toDtoList(List<Booking> bookings);
}
