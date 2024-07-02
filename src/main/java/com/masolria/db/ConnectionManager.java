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
    private final String url;
    /**
     * The User of the connection.
     */
    private final String user;
    /**
     * The Password of the connection.
     */
    private final String password;

    /**
     * Instantiates a new Connection manager.
     *
     * @param url      the url
     * @param user     the user
     * @param password the password
     */
    public ConnectionManager(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Provides connection.Invokes DriverManager getConnection() with given url, user and password
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,
                user,
                password);
    }
}