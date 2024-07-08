package com.masolria.service;

import com.masolria.Mapper.SpaceMapper;
import com.masolria.annotation.Auditable;
import com.masolria.annotation.Loggable;
import com.masolria.dto.SpaceDto;
import com.masolria.entity.Space;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.ValidationException;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import com.masolria.validator.SpaceValidator;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * The Space service.
 */
@AllArgsConstructor
@Auditable
@Loggable
public class SpaceService {
    private final SpaceMapper mapper;
    /**
     * The Space repository.
     */
    private final JdbcSpaceRepository spaceRepository;
    private static final SpaceValidator validator = SpaceValidator.getINSTANCE();

    /**
     * Retrieves all spaces from the database.
     *
     * @return A list of all spaces.
     */
    public List<SpaceDto> getAll() {
        List<Space> spaces = spaceRepository.findAll();
        return mapper.toDtoList(spaces);
    }

    /**
     * Retrieves a space by its ID.
     *
     * @param id The ID of the space to retrieve.
     * @return An Optional containing the space if found, otherwise an empty Optional.
     */
    public SpaceDto findById(Long id) throws EntityNotFoundException {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isPresent()) {
            Space space = optionalSpace.get();
            return mapper.toDto(space);
        } else throw new EntityNotFoundException();
    }

    /**
     * Saves a new space to the database.
     *
     * @param space The space to be saved.
     * @return The saved space.
     */
    public SpaceDto save(SpaceDto spaceDto) {
        if (validator.isValid(spaceDto)) {
            Space space = mapper.toEntity(spaceDto);
            Space saved = spaceRepository.save(space);
            return mapper.toDto(saved);
        } else throw new ValidationException();
    }

    /**
     * Deletes a space from the database.
     *
     * @param space The space to be deleted.
     */
    public void delete(Long id) throws EntityDeletionException {
        Optional<Space> optional = spaceRepository.findById(id);
        if (optional.isPresent()) {
            spaceRepository.delete(optional.get());
        } else throw new EntityDeletionException();
    }


    /**
     * Updates an existing space in the database.
     *
     * @param space The updated space.
     * @return The updated space.
     */
    public SpaceDto update(SpaceDto spaceDto) {
        if (validator.isValid(spaceDto)) {
            Space space = mapper.toEntity(spaceDto);
            Space updated = spaceRepository.update(space);
            return mapper.toDto(updated);
        } else throw new ValidationException();
    }
}