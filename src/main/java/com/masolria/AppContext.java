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

import java.util.HashMap;
import java.util.Map;

import static com.masolria.util.PropertiesUtil.getProperty;

/**
 * The type App context.
 */
public class AppContext {
    private static final Map<String, Object> CONTEXT = new HashMap<>();

    /**
     * Loads the authorized user into the application context.
     *
     * @param user The authorized user to be loaded.
     */
    public static void loadAuthorizedUser(User user) {
        CONTEXT.put("authorizedUser", user);
    }

    /**
     * Retrieves the authorized user from the application context.
     *
     * @return The authorized user, or null if no user is loaded.
     */
    public static User getAuthorizedUser() {
        return (User) CONTEXT.get("authorizedUser");
    }

    /**
     * Retrieves a bean from the application context by its name.
     *
     * @param beanName The name of the bean to retrieve.
     * @return The bean with the given name, or null if no such bean exists.
     */
    public static Object getBean(String beanName) {
        return CONTEXT.get(beanName);
    }


    /**
     * Loads all necessary beans and components into the application context.
     * This method  runs database migrations,
     * creates instances of services and controllers, and sets up input/output streams.
     */
    public static void loadForInjection() {
        ConnectionManager cManager = loadPropertiesToConnectionManager();
        CONTEXT.put("connectionManager", cManager);

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

    /**
     * This method initializes the database connection, runs database migrations.
      */

    public static void runMigrations() {
        ConnectionManager cManager = (ConnectionManager) getBean("connectionManager");
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner(cManager);
        liquibaseRunner.runMigration();
    }

    /**
     * Loads database connection properties from a configuration file
     * and creates a ConnectionManager instance.
     *
     * @return A ConnectionManager instance configured with database properties.
     */
    public static ConnectionManager loadPropertiesToConnectionManager() {
        String url = getProperty("postgres.url");
        String user = getProperty("postgres.user");
        String password = getProperty("postgres.password");
        return new ConnectionManager(url, user, password);
    }
}