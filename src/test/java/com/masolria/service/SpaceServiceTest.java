package com.masolria.service;

import com.masolria.Mapper.SpaceMapper;
import com.masolria.dto.SpaceDto;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SpaceServiceTest {
    @Mock
    JdbcSpaceRepository spaceRepository;
    SpaceService spaceService;
    SpaceMapper mapper = Mappers.getMapper(SpaceMapper.class);

    Space given = Space.builder()
            .id(5L)
            .spaceType(SpaceType.CONFERENCE_HALL)
            .location("some")
            .build();

    @BeforeEach
    void setUp() {
        spaceService =new SpaceService(mapper,spaceRepository);
    }

    @Test
    void update() {
        when(spaceRepository.update(given)).thenReturn(given);
        SpaceDto expected = mapper.toDto(given);
        SpaceDto actual = spaceService.update(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test//?
    void delete() {
        Mockito.doNothing().when(spaceRepository).delete(any());
        when(spaceRepository.findById(given.getId())).thenReturn(Optional.of(given));
        spaceService.delete(given.getId());
        verify(spaceRepository).delete(given);
    }
    @Test
    @DisplayName("The test checks if there are no entities with the given id, an exception is thrown")
    void deleteThrows() {
        when(spaceRepository.findById(given.getId())).thenReturn(Optional.empty());
         assertThrows(EntityDeletionException.class,()->spaceService.delete(given.getId()));
    }

    @Test
    @DisplayName("Test verifies that if space doesnt exists returns empty optional")
    void findById() {
        when(spaceRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()-> spaceService.findById(1L));

    }

    @Test
    void findAll() {
        List<Space> spaces = List.of(given);
       List<SpaceDto> expected = mapper.toDtoList(spaces);
        when(spaceRepository.findAll()).thenReturn(spaces);
        List<SpaceDto> actual = spaceService.getAll();
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    void save() {
        when(spaceRepository.save(given)).thenReturn(given);
        SpaceDto expected = mapper.toDto(given);
        SpaceDto saved = spaceService.save(expected);
        assertThat(saved).isEqualTo(expected);
    }
}