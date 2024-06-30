package com.masolria.util;

import java.io.*;
import java.util.Properties;

/**
 * The Properties util class.
 * Loads properties from resources and gives properties values.
 */
public class PropertiesUtil {
    /**
     * the object with properties
     */
    private final Properties properties = new Properties();

    /**
     * Instantiates a new Properties util.
     */
    public PropertiesUtil() {
        this.loadProperties();
    }

    /**
     * Load properties from application.properties to the properties field.
     */
    public void loadProperties() {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets property value by given key.
     *
     * @param key the String key of properties entry.
     * @return the property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}