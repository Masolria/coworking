package com.masolria.util;

import java.io.*;
import java.util.Properties;

/**
 * The Properties util class.
 * Loads properties from resources and gives properties values.
 */
public final class PropertiesUtil {
    private PropertiesUtil() {
    }

    /**
     * the object with properties
     */
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    /**
     * Load properties from application.properties to the properties field.
     */
    public static void loadProperties() {

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
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}