package com.masolria.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    private final Properties properties = new Properties();

    public PropertiesUtil() {
        this.loadProperties();
    }

    public void loadProperties() {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}