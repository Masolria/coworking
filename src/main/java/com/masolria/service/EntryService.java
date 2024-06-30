package com.masolria.service;

import com.masolria.entity.User;
import lombok.AllArgsConstructor;


import java.util.Optional;

/**
 * The Entry service.Provides registration and authorization for user.
 */
@AllArgsConstructor
public class EntryService {
    /**
     * The User service.
     */
    UserService userService;

    /**
     * Registers a new user with the given email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The newly registered user.
     * @throws IllegalArgumentException if the email or password is null, blank, or empty.
     */
    public User register(String email, String password) {
        User user;
        if (email == null || password == null || email.isBlank() || email.isEmpty() || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("cannot register, input data is invalid");
        }
        user = userService.save(User.builder().email(email).password(password).build());
        return user;
    }


    /**
     * Authorizes a user based on their email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return The authorized user if the email and password match.
     * @throws IllegalArgumentException if the user with the given email doesn't exist or if the password is incorrect.
     */
    public User authorize(String email, String password) {
        User user;
        Optional<User> userOptional = userService.getByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("user with given email doesn't exist");
        }
        user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("password is incorrect");
        }
        return user;
    }
}
