package com.masolria;

import com.masolria.controller.ConsoleController;
import com.masolria.entity.User;
import com.masolria.in.Input;
import com.masolria.out.Output;
import com.masolria.repository.Jdbc.JdbcBookingRepository;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import com.masolria.service.BookingService;
import com.masolria.service.EntryService;
import com.masolria.service.SpaceService;
import com.masolria.service.UserService;
import com.masolria.db.ConnectionManager;
import com.masolria.db.LiquibaseRunner;
import com.masolria.util.PropertiesUtil;

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
        ConnectionManager cManager = loadPropertiesToConnectionManager();
        CONTEXT.put("connectionManager",cManager);
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner(cManager);
        liquibaseRunner.runMigration();

        UserService userService = new UserService(new JdbcUserRepository(cManager));
        SpaceService spaceService = new SpaceService(new JdbcSpaceRepository(cManager));
        ConsoleController controller = new ConsoleController(userService,
                spaceService,
                new EntryService(userService),
                new BookingService(new JdbcBookingRepository(cManager), spaceService));
        CONTEXT.put("consoleController", controller);
        CONTEXT.put("input", new Input());
        CONTEXT.put("output", new Output());

    }
    public static ConnectionManager loadPropertiesToConnectionManager(){
        PropertiesUtil propertiesUtil = new PropertiesUtil();

        propertiesUtil.loadProperties();
        String url = propertiesUtil.getProperty("postgres.url");
        String user = propertiesUtil.getProperty("postgres.user");
        String password = propertiesUtil.getProperty("postgres.password");
        return new ConnectionManager(url,user,password);
    }

}
