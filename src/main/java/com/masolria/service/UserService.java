package com.masolria.service;

import com.masolria.annotation.Auditable;
import com.masolria.annotation.Loggable;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.Mapper.UserMapper;
import com.masolria.dto.UserDto;
import com.masolria.entity.User;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.EmailAlreadyInUseException;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The User service.
 */
@AllArgsConstructor
@Auditable
@Loggable
@Service
public class UserService {
    /**
     * The User repository.
     */
    private final JdbcUserRepository userRepository;
    private final UserMapper mapper;

    /**
     * Retrieves a user from the database by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public User getByEmail(String email) throws EntityNotFoundException {

        Optional<User> optional = userRepository.findByEmail(email);
        return optional.orElse(null);

    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public UserDto getById(Long id) throws EntityNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return mapper.toDto(optional.get());
        } else throw new EntityNotFoundException("Given id doesn't match any row in the table.");
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public UserDto save(AuthenticationEntry entry) throws EmailAlreadyInUseException {
        if (userRepository.findByEmail(entry.getEmail()).isEmpty()) {
            User user = User.builder()
                    .password(entry.getPassword())
                    .email(entry.getEmail())
                    .build();
            userRepository.save(user);
            return mapper.toDto(user);
        } else throw new EmailAlreadyInUseException("This email already in use by another user.");
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The updated user.
     * @return The updated user.
     */
    public UserDto update(UserDto userDto) {
        User updated = userRepository.update(mapper.toEntity(userDto));
        return mapper.toDto(updated);
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The user to be deleted.
     */
    public void delete(UserDto userDto) throws EntityNotFoundException{
        Optional<User> optional = userRepository.findById(userDto.getId());
        if (optional.isPresent()) {
            userRepository.delete(optional.get());
        } else throw new EntityDeletionException("Given dto doesn't match any row in the table.");
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return mapper.toDtoList(users);
    }
}