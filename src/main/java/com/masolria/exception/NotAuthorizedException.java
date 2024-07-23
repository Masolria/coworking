package com.masolria.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String authorizationRequired) {
        super(authorizationRequired);
    }
}