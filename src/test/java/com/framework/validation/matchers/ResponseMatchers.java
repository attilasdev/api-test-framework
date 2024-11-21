package com.framework.validation.matchers;

import io.restassured.response.Response;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ResponseMatchers {

    public static Matcher<Response> hasJsonPath(final String path) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                try {
                    response.jsonPath().get(path);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response containing JSON path '" + path + "'");
            }
        };
    }

    public static Matcher<Response> hasResponseTimeBelow(final long milliseconds) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                return response.getTime() < milliseconds;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response time below " + milliseconds + "ms");
            }
        };
    }

    public static Matcher<Response> hasContentType(final String contentType) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                String actualContentType = response.getContentType();
                return actualContentType != null && actualContentType.contains(contentType);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response with Content-Type containing '" + contentType + "'");
            }
        };
    }

    public static Matcher<Response> hasHeader(final String headerName) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                return response.getHeader(headerName) != null;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response containing header '" + headerName + "'");
            }
        };
    }

    public static Matcher<Response> hasHeaderWithValue(final String headerName, final String expectedValue) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                String actualValue = response.getHeader(headerName);
                return actualValue != null && actualValue.equals(expectedValue);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response with header '" + headerName + "' having value '" + expectedValue + "'");
            }
        };
    }

    public static Matcher<Response> hasNonEmptyBody() {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                String body = response.getBody().asString();
                return body != null && !body.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response with non-empty body");
            }
        };
    }

    public static Matcher<Response> hasJsonPathWithValue(final String path, final Object expectedValue) {
        return new TypeSafeMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response) {
                try {
                    Object actualValue = response.jsonPath().get(path);
                    return expectedValue.equals(actualValue);
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("response with JSON path '" + path + "' having value " + expectedValue);
            }
        };
    }
}