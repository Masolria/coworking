package com.masolria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Connection manager configures connection to the database.
 */
public class ConnectionManager {
    /**
     * The Url of the connection.
     */
    String URL;
    /**
     * The User of the connection.
     */
    String USER;
    /**
     * The Password of the connection.
     */
    String PASSWORD;

    /**
     * Instantiates a new Connection manager.
     *
     * @param url      the url
     * @param user     the user
     * @param password the password
     */
    public ConnectionManager(String url,String user,String password){
        URL = url;
        USER = user;
        PASSWORD = password;
    }

    /**
     * Provides connection.Invokes DriverManager getConnection() with given url, user and password
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,
                USER,
                PASSWORD);
    }
}