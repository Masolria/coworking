package service;

import entity.Space;
import repository.SpaceRepository;

import java.util.List;
import java.util.Optional;

public class SpaceService {
    SpaceRepository spaceRepository;

    public List<Space> getAll(){
        return spaceRepository.findAll();

    }

    public Optional<Space> findById(Long id){
        return spaceRepository.findById(id);
    }
    public Space save(Space space){
        return spaceRepository.save(space);
    }
    public void delete(Space space){
        spaceRepository.delete(space);
    }
    public Space update(Space space){
        return spaceRepository.update(space);
    }


}
