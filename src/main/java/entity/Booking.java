package entity;


import lombok.*;

import java.sql.Timestamp;
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
    private Timestamp bookingTimeStart;
    private Timestamp bookingTimeEnd;
    private long spaceId;
    //nullable
    private User bookeddForUser;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;

        return isBooked == booking.isBooked && spaceId == booking.spaceId && Objects.equals(id, booking.id) && Objects.equals(bookingTimeStart, booking.bookingTimeStart) && Objects.equals(bookingTimeEnd, booking.bookingTimeEnd) && Objects.equals(bookeddForUser, booking.bookeddForUser);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Boolean.hashCode(isBooked);
        result = 31 * result + Objects.hashCode(bookingTimeStart);
        result = 31 * result + Objects.hashCode(bookingTimeEnd);
        result = 31 * result + Long.hashCode(spaceId);
        result = 31 * result + Objects.hashCode(bookeddForUser);
        return result;
    }
}
