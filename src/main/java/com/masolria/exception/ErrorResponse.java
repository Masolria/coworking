package com.masolria.exception;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponse {
    private int httpStatus;
    private String message;
    @Builder.Default
    private LocalDateTime dateTime =LocalDateTime.now();
}