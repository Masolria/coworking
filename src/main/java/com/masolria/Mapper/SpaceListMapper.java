package com.masolria.Mapper;

import com.masolria.dto.SpaceDto;
import com.masolria.entity.Space;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = SpaceMapper.class)
public interface SpaceListMapper {
    List<SpaceDto> toDtoList(List<Space> spaces);
}
