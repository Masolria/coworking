package com.masolria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.masolria.controller.rest")
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleUnknown(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = {ValidationException.class, EntityDeletionException.class})
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(OccupiedConflictException.class)
    public ResponseEntity<ErrorResponse> handleOccupiedConflict(OccupiedConflictException e) {
        return buildErrorResponse(HttpStatus.CONFLICT,e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse response = ErrorResponse.builder()
                .httpStatus(status.value())
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}