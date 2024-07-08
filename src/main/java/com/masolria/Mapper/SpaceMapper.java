package com.masolria.Mapper;

import com.masolria.dto.SpaceDto;
import com.masolria.entity.Space;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SpaceMapper {
    @Mapping(target = "spaceType",source ="spaceType")
    SpaceDto toDto(Space space);
    Space toEntity(SpaceDto spaceDto);

    List<SpaceDto> toDtoList(List<Space> spaces);

}
