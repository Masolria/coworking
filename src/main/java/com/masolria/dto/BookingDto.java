package com.masolria.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class BookingDto {
    private final Long id;
    private final boolean isBooked;
    private final LocalDateTime timeStart;
    private final LocalDateTime timeEnd;
    private final Long spaceId;
    private final Long forUserId;
}