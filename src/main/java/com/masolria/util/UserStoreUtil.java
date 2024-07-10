package com.masolria.util;

import com.masolria.dto.UserDto;

/**
 * This class stores authorized user during
 */
public class UserStoreUtil {
    private static final ThreadLocal<UserDto> userAuthorized = new ThreadLocal<>();

    public static UserDto getUserAuthorized() {
        return userAuthorized.get();
    }

    public static void setUserAuthorized(UserDto user) {
        userAuthorized.set(user);
    }
    public static void clearUserAuthorized(){
        userAuthorized.remove();
    }
}
