package com.masolria.service;

import com.masolria.Mapper.UserMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.repository.Jdbc.JdbcUserRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    JdbcUserRepository userRepository;

    UserService userService;
    UserMapper mapper = Mappers.getMapper(UserMapper.class);


    User given = User.builder()
            .id(3L)
            .email("some@mail.com")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository,mapper);
    }

    @Test
    void update() {
        User user = User.builder().id(3L)
                .email("some@mail.com")
                .build();
        when(userRepository.update(user)).thenReturn(user);
        UserDto expected= mapper.toDto(given);
        UserDto actual = userService.update(expected);
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.email()).isEqualTo(expected.email());

    }

    @Test
    void delete() {
        Mockito.doNothing().when(userRepository).delete(given);
        when(userRepository.findById(given.getId())).thenReturn(Optional.of(given));
        UserDto expected= mapper.toDto(given);
        userService.delete(expected);
        verify(userRepository).delete(given);
    }
    @Test
    void deleteThrows() {

        when(userRepository.findById(given.getId())).thenReturn(Optional.empty());
        UserDto expected= mapper.toDto(given);
       assertThrows(EntityDeletionException.class,()->userService.delete(expected));

    }

    @Test
    @DisplayName("Test verifies that if space doesnt exists returns empty optional")
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->userService.getById(1L));
    }

    @Test
    void findAll() {
        List<User> users = List.of(given);
        List<UserDto> expected = mapper.toDtoList(users);
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> actual = userService.getAll();
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    void save() {
        User user = User.builder().email("some@mail.com").password("password").build();
        when(userRepository.save(user)).thenReturn(user);
        AuthenticationEntry entry = new AuthenticationEntry(user.getEmail(), user.getPassword());
        UserDto saved = userService.save(entry);
        assertThat(saved.email()).isEqualTo(entry.email());
    }
}