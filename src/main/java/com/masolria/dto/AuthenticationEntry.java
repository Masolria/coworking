package com.masolria.dto;

import lombok.Data;

@Data
public final class AuthenticationEntry {
    private final String email;
    private final String password;

}
