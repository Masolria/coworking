package com.masolria.service;

import com.masolria.entity.User;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * The User service.
 */
@AllArgsConstructor
public class UserService {
    /**
     * The User repository.
     */
    JdbcUserRepository userRepository;

    /**
     * Retrieves a user from the database by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The updated user.
     * @return The updated user.
     */
    public User update(User user) {
        return userRepository.update(user);
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The user to be deleted.
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }
}