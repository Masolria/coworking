package com.masolria.exception;

/**
 * The Occupied conflict exception.Is thrown when there is a booking conflict.
 * When tries to book a slot, it is already busy though.
 */
public class OccupiedConflictException extends RuntimeException {
    /**
     * Instantiates a new Occupied conflict exception.
     */

    public OccupiedConflictException(String message) {
        super(message);
    }
}
