package com.framework.performance.config;

public class PerformanceConfig {
    public static final int CONCURRENT_USERS = 50;
    public static final int REQUESTS_PER_USER = 10;
    public static final long ACCEPTABLE_RESPONSE_TIME = 500; // ms
    public static final String BASE_URL = "http://localhost:8090";
    public static final int PORT = 8090;
}