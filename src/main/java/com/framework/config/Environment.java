package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {
    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("env", "qa");
        try (InputStream input = Environment.class.getResourceAsStream("/config/" + env + ".properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}