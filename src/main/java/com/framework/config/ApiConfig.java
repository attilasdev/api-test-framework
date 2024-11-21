package com.framework.config;

public class ApiConfig {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    
    private String baseUrl;
    private String apiToken;

    public ApiConfig() {
        this.baseUrl = System.getProperty("baseUrl", DEFAULT_BASE_URL);
        this.apiToken = System.getProperty("apiToken", "");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiToken() {
        return apiToken;
    }
}