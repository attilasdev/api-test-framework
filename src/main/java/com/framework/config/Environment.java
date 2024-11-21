package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {
    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("env", "test");
        loadProperties(env);
        loadTestProperties();
    }

    private static void loadProperties(String env) {
        try (InputStream input = Environment.class.getResourceAsStream("/config/" + env + ".properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties", e);
        }
    }

    private static void loadTestProperties() {
        try (InputStream input = Environment.class.getResourceAsStream("/config/test.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}