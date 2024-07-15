package com.masolria.dto;


import lombok.Data;

@Data
public final class SpaceDto {
    private final Long id;
    private final String location;
    private final String spaceType;
}