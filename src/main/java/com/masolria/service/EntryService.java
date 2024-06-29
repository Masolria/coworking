package com.masolria.service;

import com.masolria.entity.User;
import lombok.AllArgsConstructor;


import java.util.Optional;
@AllArgsConstructor
public class EntryService {
    UserService userService;

    public User register(String email, String password) {
        User user;
        if (email == null || password == null || email.isBlank() || email.isEmpty() || password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("cannot register, input data is invalid");
        }
        user = userService.save(User.builder().email(email).password(password).build());
        return user;
    }


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
