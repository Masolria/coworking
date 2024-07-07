package com.masolria.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.Mapper.UserMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.annotations.Auditable;
import com.masolria.annotations.Loggable;
import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import com.masolria.exception.AlreadyRegisteredException;
import com.masolria.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;

import java.util.Optional;

/**
 * The Entry service.Provides registration and authorization for user.
 */
@AllArgsConstructor
@Auditable
public class EntryService {
    /**
     * The User service.
     */
    UserService userService;
    UserMapper userMapper;

    /**
     * Registers a new user with the given email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The newly registered user.
     * @throws IllegalArgumentException if the email or password is null, blank, or empty.
     */
    public UserDto register(AuthenticationEntry entry) throws IllegalArgumentException {
        String password = entry.password();
        String email = entry.email();
        if (email == null || password == null || email.isBlank() || email.isEmpty() || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("cannot register, input data is invalid");
        } else if (userService.getByEmail(email) != null) {
            throw new AlreadyRegisteredException();
        }
        return userService.save(entry);
    }

    /**
     * Authorizes a user based on their email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The authorized user if the email and password match.
     * @throws IllegalArgumentException if the user with the given email doesn't exist or if the password is incorrect.
     */
    @Loggable
    public UserDto authorize(AuthenticationEntry entry) {
        String email = entry.email();
        String password = entry.password();
        try {
            Optional<User> optional = userService.userRepository.findByEmail(email);
            if (optional.isEmpty()) {
                throw new EntityNotFoundException();
            }
            if (password.equals(optional.get().getPassword())) {
                return userMapper.toDto(optional.get());
            }
            throw new IllegalArgumentException("password is incorrect");
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("user with given email doesn't exist");
        }
    }
}
