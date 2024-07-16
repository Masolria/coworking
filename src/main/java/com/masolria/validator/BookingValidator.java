package com.masolria.validator;

import com.masolria.dto.BookingDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class BookingValidator implements Validator<BookingDto>{
   @Getter
   private static final BookingValidator INSTANCE = new BookingValidator();

    @Override
    public boolean isValid(BookingDto object) {
        return object.getSpaceId() != null && object.getTimeStart() != null && object.getTimeEnd() != null;
    }
    private BookingValidator() {}
}