package com.masolria.entity;

import lombok.*;

import java.util.Objects;

/**
 * The type User.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    /**
     *  The identifier for the User record.
     */
    private Long id;
    /**
     *  The User email for authorization.
     */
    private String email;
    /**
     *  The User password for authorization.
     */
    private String password;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(password);
        return result;
    }
}
