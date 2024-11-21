package com.framework.base;

import com.framework.client.RestClient;
import com.framework.config.ApiConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected RestClient restClient;
    protected ApiConfig config;

    @BeforeAll
    public void setUp() {
        config = new ApiConfig();
        restClient = new RestClient(config);
    }
}