package com.masolria.entity;

import lombok.*;
import java.util.Objects;

/**
 * The Space entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Space {
    /**
     *  The identifier for the Space record.
     */
    private Long id;
    /**
     *  The physical location described in the string.
     */
    private String location;
    /**
     *  The type of space. WORKING_SPACE or CONFERENCE_HALL
     */
    private SpaceType spaceType;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Space space)) return false;

        return Objects.equals(id, space.id) && Objects.equals(location, space.location) && spaceType == space.spaceType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(location);
        result = 31 * result + Objects.hashCode(spaceType);
        return result;
    }
}