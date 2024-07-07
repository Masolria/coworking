package com.masolria.util;

import com.masolria.dto.UserDto;

/**
 * This class stores authorized user during
 */
public class UserStoreUtil {
    private static UserDto userAuthorized;

    public UserDto getUserAuthorized() {
        return userAuthorized;
    }

    public void setUserAuthorized(UserDto userAuthorized) {
        this.userAuthorized = userAuthorized;
    }
}
