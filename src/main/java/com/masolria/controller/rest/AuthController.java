package com.masolria.controller.rest;

import com.masolria.dto.AuthenticationEntry;
import com.masolria.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth",produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> register(@RequestBody AuthenticationEntry authEntry){
        authService.register(authEntry);

      //  HttpHeaders headers = new HttpHeaders();
      //  headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+token);
        return ResponseEntity.status(HttpStatus.CREATED).body("You're successfully registered." +
                                                              " Now you are allowed to interact.");
    }

    @PostMapping(value = "/authorize",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> authorize(@RequestBody AuthenticationEntry authEntry){
        authService.authorize(authEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body("You're successfully registered." +
                                                              "\nNow you are allowed to interact.");
    }
}