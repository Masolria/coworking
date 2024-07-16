package com.masolria.controller.rest;

import com.masolria.dto.AuthenticationEntry;
import com.masolria.dto.UserDto;
import com.masolria.service.AuthService;
import com.masolria.util.UserStoreUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> register(@RequestBody AuthenticationEntry authEntry) {
        UserDto userDto = authService.register(authEntry);
        UserStoreUtil.setUserAuthorized(userDto);
        String token = "token";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("You're successfully registered." +
                                                                               " Now you are allowed to interact.");
    }

    @PostMapping(value = "/authorize", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> authorize(@RequestBody AuthenticationEntry authEntry) {
        UserDto userDto = authService.authorize(authEntry);
        UserStoreUtil.setUserAuthorized(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("You're successfully authorized. Now you are allowed to interact.");
    }
}