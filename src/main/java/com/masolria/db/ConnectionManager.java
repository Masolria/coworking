package com.masolria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    String URL;
    String USER;
    String PASSWORD;
    public ConnectionManager(String url,String user,String password){
        URL = url;
        USER = user;
        PASSWORD = password;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,
                USER,
                PASSWORD);
    }
}