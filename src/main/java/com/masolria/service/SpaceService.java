package com.masolria.service;

import com.masolria.entity.Space;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * The Space service.
 */
@AllArgsConstructor
public class SpaceService {
    /**
     * The Space repository.
     */
    JdbcSpaceRepository spaceRepository;

    /**
     * Retrieves all spaces from the database.
     *
     * @return A list of all spaces.
     */
    public List<Space> getAll() {
        return spaceRepository.findAll();

    }

    /**
     * Retrieves a space by its ID.
     *
     * @param id The ID of the space to retrieve.
     * @return An Optional containing the space if found, otherwise an empty Optional.
     */
    public Optional<Space> findById(Long id) {
        return spaceRepository.findById(id);
    }

    /**
     * Saves a new space to the database.
     *
     * @param space The space to be saved.
     * @return The saved space.
     */
    public Space save(Space space) {
        return spaceRepository.save(space);
    }

    /**
     * Deletes a space from the database.
     *
     * @param space The space to be deleted.
     */
    public void delete(Space space) {
        spaceRepository.delete(space);
    }

    /**
     * Updates an existing space in the database.
     *
     * @param space The updated space.
     * @return The updated space.
     */
    public Space update(Space space) {
        return spaceRepository.update(space);
    }
}