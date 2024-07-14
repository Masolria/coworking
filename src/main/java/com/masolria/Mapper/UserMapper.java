package com.masolria.Mapper;

import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
}
