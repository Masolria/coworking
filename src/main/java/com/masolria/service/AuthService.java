package com.masolria.service;

import com.masolria.Mapper.UserMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.annotation.Auditable;
import com.masolria.annotation.Loggable;
import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import com.masolria.exception.AlreadyRegisteredException;
import com.masolria.exception.UserNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The Entry service.Provides registration and authorization for user.
 */
@Auditable
@Loggable
@Service
@AllArgsConstructor
public class AuthService {
    /**
     * The User service.
     */
   private final  UserService userService;

    private final UserMapper userMapper;


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
            throw new AlreadyRegisteredException("User with given email already exists");
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

    public UserDto authorize(AuthenticationEntry entry) {
        String email = entry.email();
        String password = entry.password();
        User user = userService.getByEmail(email);
        if (user==null) {
            throw new UserNotExistException("User with given email doesn't exist");
        }
        if (password.equals(user.getPassword())) {
            return userMapper.toDto(user);
        }
        throw new IllegalArgumentException("password is incorrect");
    }
}