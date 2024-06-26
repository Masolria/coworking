package com.masolria.repository;

import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;


import java.util.*;

public class SpaceRepository {
    private final Map<Long, Space> spaces = new HashMap<>();
    private Long id = 1L;

    public SpaceRepository(){
        spaces.put(id,Space.builder().id(id++).location("1st floor room 1").spaceType(SpaceType.WorkingSpace).build());
        spaces.put(id,Space.builder().id(id++).location("2nd floor room 22").spaceType(SpaceType.ConferenceHall).build());
    }

    public Space save(Space space) {
        space.setId(id++);
        spaces.put(space.getId(), space);
        return space;
    }


    public Space update(Space space) {
        if (spaces.containsKey(space.getId())) {
            spaces.put(space.getId(), space);
        } else {
            return save(space);
        }
        return space;
    }

    public void delete(Space space) {
        spaces.remove(space.getId());
    }

    public Optional<Space> findById(Long id) {
        return Optional.ofNullable(spaces.get(id));
    }

    public List<Space> findAll() {
        return new ArrayList<>(spaces.values());
    }

}
