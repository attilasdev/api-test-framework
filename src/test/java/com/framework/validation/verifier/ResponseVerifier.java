package com.framework.validation.verifier;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import java.util.function.Consumer;

public class ResponseVerifier {
    private final Response response;

    public ResponseVerifier(Response response) {
        this.response = response;
    }

    public static ResponseVerifier verify(Response response) {
        return new ResponseVerifier(response);
    }

    public ResponseVerifier statusCode(int expectedStatusCode) {
        StatusCodeVerifier.verify(response, expectedStatusCode);
        return this;
    }

    public ResponseVerifier schema(String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
        return this;
    }

    public ResponseVerifier assertResponse(Consumer<Response> assertion) {
        assertion.accept(response);
        return this;
    }
}