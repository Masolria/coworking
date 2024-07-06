package com.masolria.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.Mapper.*;
import com.masolria.db.ConnectionManager;
import com.masolria.db.LiquibaseRunner;

import com.masolria.repository.Jdbc.JdbcBookingRepository;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import com.masolria.service.BookingService;
import com.masolria.service.EntryService;
import com.masolria.service.SpaceService;
import com.masolria.service.UserService;
import com.masolria.util.PropertiesUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.mapstruct.factory.Mappers;

import static com.masolria.util.PropertiesUtil.getProperty;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();
        createConnectionManager(context);
        initMappers(context);
        initService(context);
        liquibaseConfigure(context);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    private void createConnectionManager(ServletContext servletContext) {
        PropertiesUtil.loadProperties();
        ConnectionManager connectionManager = new ConnectionManager(getProperty("postgres.url"),
                getProperty("postgres.user"),
                getProperty("postgres.password"),
                getProperty("postgres.driver"));
        servletContext.setAttribute("connectionManager",connectionManager);

    }

    private void initMappers(ServletContext sc) {
        sc.setAttribute("objectMapper", new ObjectMapper());

        sc.setAttribute("spaceMapper", Mappers.getMapper(SpaceMapper.class));
        sc.setAttribute("bookingMapper", Mappers.getMapper(BookingMapper.class));
        sc.setAttribute("userMapper",Mappers.getMapper(UserMapper.class));

        sc.setAttribute("spaceListMapper", Mappers.getMapper(SpaceListMapper.class));
        sc.setAttribute("bookingListMapper", Mappers.getMapper(BookingListMapper.class));
    }

    private void initService(ServletContext sc) {
        ConnectionManager cManager = (ConnectionManager) sc.getAttribute("connectionManager");
        SpaceService spaceService = new SpaceService((SpaceMapper) sc.getAttribute("spaceMapper"),
                (SpaceListMapper) sc.getAttribute("spaceListMapper"),
                new JdbcSpaceRepository((cManager)));

        BookingService bookingService = new BookingService(new JdbcBookingRepository(cManager),
                spaceService, (BookingListMapper) sc.getAttribute("bookingListMapper"),
                (BookingMapper) sc.getAttribute("bookingMapper"));

        UserService userService = new UserService(new JdbcUserRepository(cManager),
                (UserMapper) sc.getAttribute("userMapper"),
                (UserListMapper) sc.getAttribute("userListMapper"));
        EntryService entryService = new EntryService(userService);
        sc.setAttribute("spaceService", spaceService);
        sc.setAttribute("bookingService", bookingService);
        sc.setAttribute("userService",userService);
        sc.setAttribute("entryService,",entryService);
    }
    private void liquibaseConfigure(ServletContext sc){
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner((ConnectionManager) sc.getAttribute("connectionManager"));
        String enabled = getProperty("liquibase.enabled");
        if(enabled.equals("true")){
            liquibaseRunner.runMigration();
        }
        sc.setAttribute("liquibaseRunner",liquibaseRunner);
    }
}