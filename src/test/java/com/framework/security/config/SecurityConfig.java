package com.framework.security.config;

public class SecurityConfig {
    public static final String BASE_URL = "http://localhost:8089";
    public static final String VALID_TOKEN = "valid_token";
    public static final String[] REQUIRED_SECURITY_HEADERS = {
        "X-Content-Type-Options",
        "X-Frame-Options",
        "X-XSS-Protection"
    };
}