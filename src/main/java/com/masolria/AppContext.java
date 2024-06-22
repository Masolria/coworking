package com.masolria;

import com.masolria.InputOutput.*;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.User;
import com.masolria.repository.BookingRepository;
import com.masolria.repository.SpaceRepository;
import com.masolria.repository.UserRepository;
import com.masolria.service.BookingService;
import com.masolria.service.EntryService;
import com.masolria.service.SpaceService;
import com.masolria.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private static final Map<String, Object> CONTEXT = new HashMap<>();

    public static void loadAuthorizedUser(User user) {
        CONTEXT.put("authorizedUser", user);
    }

    public static User getAuthorizedUser() {
        return (User) CONTEXT.get("authorizedUser");
    }
    public static Object getBean(String beanName){
        return CONTEXT.get(beanName);
    }

    public static void loadForInjection() {
        UserService userService = new UserService(new UserRepository());
        SpaceService spaceService = new SpaceService(new SpaceRepository());
        ConsoleController controller = new ConsoleController(userService,
                spaceService,
                new EntryService(userService),
                new BookingService(new BookingRepository(), spaceService));
        CONTEXT.put("consoleController", controller);
        CONTEXT.put("input", new Input());
        CONTEXT.put("output", new Output());
    }
}
