package com.masolria.repository;

import com.masolria.entity.User;

import java.util.*;

public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long id = 1L;

    public UserRepository() {
        User defaultUser = User.builder()
                .id(id++)
                .email("default@mail.com")
                .password("password")
                .build();
        users.put(defaultUser.getId(),defaultUser);
    }

    public User save(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    public void delete(User user) {
        users.remove(user);
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
           return save(user);
        }
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
       return Optional.empty();
    }
}
