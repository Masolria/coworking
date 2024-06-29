package com.masolria.db;

import com.masolria.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public Connection getConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
    public Connection getConnection() throws SQLException {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        String URL = propertiesUtil.getProperty("postgres.url");
        String USER = propertiesUtil.getProperty("postgres.user");
        String PASSWORD = propertiesUtil.getProperty("postgres.password");
        return DriverManager.getConnection(URL,
                USER,
                PASSWORD);
    }
}
