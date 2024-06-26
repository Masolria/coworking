package com.masolria.entity;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Booking {
    private Long id;
    private boolean isBooked;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Long spaceId ;
    //nullable
    private Long bookedForUserId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;

        return isBooked == booking.isBooked && Objects.equals(id, booking.id) && Objects.equals(timeStart, booking.timeStart) && Objects.equals(timeEnd, booking.timeEnd) && Objects.equals(spaceId, booking.spaceId) && Objects.equals(bookedForUserId, booking.bookedForUserId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Boolean.hashCode(isBooked);
        result = 31 * result + Objects.hashCode(timeStart);
        result = 31 * result + Objects.hashCode(timeEnd);
        result = 31 * result + Objects.hashCode(spaceId);
        result = 31 * result + Objects.hashCode(bookedForUserId);
        return result;
    }
}
