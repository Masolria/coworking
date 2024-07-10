package com.masolria.service;

import com.masolria.entity.User;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    JdbcUserRepository userRepository;
    @InjectMocks
    UserService userService;

    User given = User.builder()
            .id(3L)
            .email("some@mail.com")
            .password("password")
            .build();
    @Test
    void update() {
        when(userRepository.update(given)).thenReturn(given);
        User actual = userService.update(given);
        assertThat(actual).isEqualTo(given);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(userRepository).delete(given);
        userService.delete(given);
        verify(userRepository).delete(given);
    }

    @Test
    @DisplayName("Test verifies that if space doesnt exists returns empty optional")
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<User> optional = userService.getById(1L);
        assertThat(optional).isEmpty();
    }

    @Test
    void findAll() {
        List<User> users = List.of(given);
        when(userRepository.findAll()).thenReturn(users);
        List<User> actual = userService.getAll();
        assertThat(actual).isEqualTo(users);

    }
    @Test
    void save() {
        when(userRepository.save(given)).thenReturn(given);
        User saved = userService.save(given);
        assertThat(saved).isEqualTo(given);
    }
}