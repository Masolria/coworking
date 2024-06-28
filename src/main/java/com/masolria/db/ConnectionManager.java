package com.masolria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public Connection getConnection() throws SQLException {
        String URL = "jdbc:postgresql://localhost:5433/coworking_db";
        String USER = "coworking_user";
        String PASSWORD = "coworkpassword";
        return DriverManager.getConnection(URL,
                USER,
                PASSWORD);
    }
}
