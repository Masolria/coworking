package repository;

import entity.User;

import java.util.*;

public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long id = 1L;
    {
        User defaultUser = User.builder()
                .id(id++)
                .email("default@mail.com")
                .password("password")
                .build();
    }
    public User save(User user){
        user.setId(id++);
        users.put(user.getId(),user);
        return user;
    }
    public void delete(User user){
        users.remove(user);
    }

    public User update(User user){
        if (users.containsKey(user.getId())){
            users.put(user.getId(),user);
        }else {
            user.setId(id++);
         save(user);
        }
        return user;
    }
    public Optional<User> findById(Long id){
        return Optional.ofNullable(users.get(id));
    }
    public List<User> findAll(){
        return new ArrayList<>(users.values());
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}
