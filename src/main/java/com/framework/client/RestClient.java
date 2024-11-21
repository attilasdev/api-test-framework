package com.framework.client;

import com.framework.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;

public class RestClient {
    private final RequestSpecification requestSpec;

    public RestClient(ApiConfig config) {
        RestAssured.config = RestAssuredConfig.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        requestSpec = new RequestSpecBuilder()
            .setBaseUri(config.getBaseUrl())
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation() // Add this for HTTPS
            .build();

        if (!config.getApiToken().isEmpty()) {
            requestSpec.header("Authorization", "Bearer " + config.getApiToken());
        }
    }

    public RequestSpecification getRequestSpec() {
        return RestAssured.given()
            .spec(requestSpec)
            .log().all();  // Log all requests
    }
}