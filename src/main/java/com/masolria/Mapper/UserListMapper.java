package com.masolria.Mapper;

import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> toDtoList(List<User> userList);
}
