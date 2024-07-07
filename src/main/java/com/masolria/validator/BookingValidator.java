package com.masolria.validator;

import com.masolria.dto.BookingDto;
import lombok.Getter;

public class BookingValidator implements Validator<BookingDto>{
   @Getter
   private static final BookingValidator INSTANCE = new BookingValidator();

    @Override
    public boolean isValid(BookingDto object) {
        return object.spaceId() != null && object.timeStart() != null && object.timeEnd() != null;
    }
    private BookingValidator() {}
}