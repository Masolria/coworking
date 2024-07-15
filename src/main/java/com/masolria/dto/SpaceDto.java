package com.masolria.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpaceDto {
    private  Long id;
    private  String location;
    private  String spaceType;
}