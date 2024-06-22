package com.masolria.entity;

import com.masolria.entity.enums.SpaceType;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Space {
    private Long id;
    private String location;
    private SpaceType spaceType;
    //вводишь время и название комнаты, если свободно-бронируется, если нет, то отказывается в брони(исключение)

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
