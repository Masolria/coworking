package com.masolria.validator;

import com.masolria.dto.SpaceDto;
import lombok.Getter;

public class SpaceValidator implements Validator<SpaceDto> {
    @Getter
    private static final SpaceValidator INSTANCE = new SpaceValidator();
    @Override
    public boolean isValid(SpaceDto object) {
        return object.getSpaceType() != null && object.getLocation() != null;
    }
    private SpaceValidator() {}
}
