package com.masolria.exception;

public class OccupiedConflictException extends RuntimeException {
    public OccupiedConflictException(String string) {
        System.out.println(string);
    }
}
