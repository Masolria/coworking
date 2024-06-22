package com.masolria.service;

import com.masolria.entity.Space;
import com.masolria.repository.SpaceRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SpaceService {
    SpaceRepository spaceRepository;

    public List<Space> getAll() {
        return spaceRepository.findAll();

    }



    public Optional<Space> findById(Long id) {
        return spaceRepository.findById(id);
    }

    public Space save(Space space) {
        return spaceRepository.save(space);
    }

    public void delete(Space space) {
        spaceRepository.delete(space);
    }

    public Space update(Space space) {
        return spaceRepository.update(space);
    }


}
