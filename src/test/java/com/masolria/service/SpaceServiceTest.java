package com.masolria.service;

import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SpaceServiceTest {
    @Mock
    JdbcSpaceRepository spaceRepository;
    @InjectMocks
    SpaceService spaceService;

    Space given = Space.builder()
            .id(5L)
            .spaceType(SpaceType.CONFERENCE_HALL)
            .location("some")
            .build();
    @Test
    void update() {
        when(spaceRepository.update(given)).thenReturn(given);
        Space actual = spaceService.update(given);
        assertThat(actual).isEqualTo(given);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(spaceRepository).delete(given);
        spaceService.delete(given);
        verify(spaceRepository).delete(given);
    }

    @Test
    @DisplayName("Test verifies that if space doesnt exists returns empty optional")
    void findById() {
        when(spaceRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Space> optional = spaceService.findById(1L);
        assertThat(optional).isEmpty();
    }

    @Test
    void findAll() {
        List<Space> spaces = List.of(given);
        when(spaceRepository.findAll()).thenReturn(spaces);
        List<Space> actual = spaceService.getAll();
        assertThat(actual).isEqualTo(spaces);

    }
    @Test
    void save() {
        when(spaceRepository.save(given)).thenReturn(given);
        Space saved = spaceService.save(given);
        assertThat(saved).isEqualTo(given);
    }

}