package repository;

import entity.Space;

import entity.enums.SpaceType;

import java.util.*;

public class SpaceRepository {
    private  final Map<Long, Space> spaces = new HashMap<>();
    private Long id = 1L;

    {
        Space workingSpace1 = Space.builder().id(id++).location("1st floor room 1").spaceType(SpaceType.WorkingSpace).build();
        Space workingSpace2 = Space.builder().id(id++).location("1st floor room 11").spaceType(SpaceType.WorkingSpace).build();
        Space conferenceHall1 = Space.builder().id(id++).location("2st floor room 21").spaceType(SpaceType.ConferenceHall).build();
        Space conferenceHall2 = Space.builder().id(id++).location("2st floor room 22").spaceType(SpaceType.ConferenceHall).build();

    }

    public Space save(Space space) {
        space.setId(id++);
        spaces.put(space.getId(), space);
        return space;
    }


    public Space update(Space space){
        if (spaces.containsKey(space.getId())){
            spaces.put(space.getId(),space);
        }else {
            space.setId(id++);
            save(space);
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
