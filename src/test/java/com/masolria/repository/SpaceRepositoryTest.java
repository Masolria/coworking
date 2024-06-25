package com.masolria.repository;

import com.masolria.entity.Space;
import com.masolria.entity.enums.SpaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class SpaceRepositoryTest {
    private final SpaceRepository spaceRepository = new SpaceRepository();
    private Space given;

    @BeforeEach
    public void setupTestData() {
        given = Space.builder().location("3rd floor room 33").spaceType(SpaceType.ConferenceHall).id(3L).build();
    }

    @Test
    @DisplayName("junit test asserts saving space obj")
    void save() {
        Space created = spaceRepository.save(given);
        assertThat(created).isEqualTo(given);
    }

    @Test
    @DisplayName("junit test for update operation")
    void update() {
        spaceRepository.save(given);
        Space space = spaceRepository.findById(given.getId()).get();
        space.setSpaceType(SpaceType.ConferenceHall);
        space.setLocation("new location");
        Space updated = spaceRepository.update(space);
        assertThat(space.getLocation()).isEqualTo("new location");
        assertThat(updated).isNotNull();
    }

    @Test
    void delete() {
        Space saved = spaceRepository.save(given);
        spaceRepository.delete(saved);
        Optional<Space> emptyOptional = spaceRepository.findById(saved.getId());
        assertThat(emptyOptional).isEmpty();
    }

}