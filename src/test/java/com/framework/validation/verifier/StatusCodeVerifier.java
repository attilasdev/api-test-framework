package com.framework.validation.verifier;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

public class StatusCodeVerifier {
    
    public static void verify(Response response, int expectedStatusCode) {
        Assertions.assertThat(response.getStatusCode())
            .as("Response status code")
            .isEqualTo(expectedStatusCode);
    }

    public static void verifySuccess(Response response) {
        int statusCode = response.getStatusCode();
        Assertions.assertThat(statusCode)
            .as("Response status code")
            .isBetween(200, 299);
    }

    public static void verifyClientError(Response response) {
        int statusCode = response.getStatusCode();
        Assertions.assertThat(statusCode)
            .as("Response status code")
            .isBetween(400, 499);
    }

    public static void verifyServerError(Response response) {
        int statusCode = response.getStatusCode();
        Assertions.assertThat(statusCode)
            .as("Response status code")
            .isBetween(500, 599);
    }

    public static void verifyCreated(Response response) {
        verify(response, 201);
    }

    public static void verifyNoContent(Response response) {
        verify(response, 204);
    }

    public static void verifyBadRequest(Response response) {
        verify(response, 400);
    }

    public static void verifyUnauthorized(Response response) {
        verify(response, 401);
    }

    public static void verifyForbidden(Response response) {
        verify(response, 403);
    }

    public static void verifyNotFound(Response response) {
        verify(response, 404);
    }
}