package com.masolria.dto;

import java.time.LocalDateTime;

public record BookingDto(Long id,
        boolean isBooked,
        LocalDateTime timeStart,
        LocalDateTime timeEnd,
        Long spaceId,
        Long forUserId) {}