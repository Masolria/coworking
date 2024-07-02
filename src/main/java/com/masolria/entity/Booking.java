package com.masolria.entity;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Booking entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Booking {
    /**
     *  The identifier for the booking record.
     */
    private Long id;
    /**
     *  The field represents whether booking slot is free or not.
     */
    private boolean isBooked;
    /**
     *  The opening date and time of the current booking slot.
     */
    private LocalDateTime timeStart;
    /**
     *  The closing date and time of the current booking slot.
     */
    private LocalDateTime timeEnd;
    /**
     *  The identifier of the space record for which the reservation is being made.
     */
    private Long spaceId ;
    /**
     *  The identifier of the user for which the reservation is being made.
     *  May be nullable.
     */
    private Long forUserId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;

        return isBooked == booking.isBooked && Objects.equals(id, booking.id) && Objects.equals(timeStart, booking.timeStart) && Objects.equals(timeEnd, booking.timeEnd) && Objects.equals(spaceId, booking.spaceId) && Objects.equals(forUserId, booking.forUserId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Boolean.hashCode(isBooked);
        result = 31 * result + Objects.hashCode(timeStart);
        result = 31 * result + Objects.hashCode(timeEnd);
        result = 31 * result + Objects.hashCode(spaceId);
        result = 31 * result + Objects.hashCode(forUserId);
        return result;
    }
}
