package com.masolria.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDto {
    private  Long id;
    private  String location;
    private  String spaceType;
}