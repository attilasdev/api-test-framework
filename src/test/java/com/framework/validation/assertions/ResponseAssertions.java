package com.framework.validation.assertions;

import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import java.util.List;
import java.util.Map;

public class ResponseAssertions extends AbstractAssert<ResponseAssertions, Response> {
    
    private ResponseAssertions(Response response) {
        super(response, ResponseAssertions.class);
    }

    public static ResponseAssertions assertThat(Response response) {
        return new ResponseAssertions(response);
    }

    public ResponseAssertions hasStatusCode(int expectedStatusCode) {
        isNotNull();
        if (actual.getStatusCode() != expectedStatusCode) {
            failWithMessage("Expected status code <%s> but was <%s>",
                expectedStatusCode, actual.getStatusCode());
        }
        return this;
    }

    public ResponseAssertions hasResponseTime(long maxTime) {
        isNotNull();
        if (actual.getTime() > maxTime) {
            failWithMessage("Response time <%s> exceeded maximum <%s>",
                actual.getTime(), maxTime);
        }
        return this;
    }

    public ResponseAssertions hasHeader(String headerName) {
        isNotNull();
        if (!actual.getHeaders().hasHeaderWithName(headerName)) {
            failWithMessage("Expected header <%s> not found", headerName);
        }
        return this;
    }

    public ResponseAssertions hasHeaderValue(String headerName, String expectedValue) {
        isNotNull();
        String actualValue = actual.getHeader(headerName);
        if (!expectedValue.equals(actualValue)) {
            failWithMessage("Expected header <%s> to have value <%s> but was <%s>",
                headerName, expectedValue, actualValue);
        }
        return this;
    }

    public ResponseAssertions hasJsonPath(String path) {
        isNotNull();
        if (actual.jsonPath().get(path) == null) {
            failWithMessage("JSON path <%s> not found in response", path);
        }
        return this;
    }
}