package com.framework.validation.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class CustomMatchers {
    
    public static Matcher<String> isISODateTime() {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                try {
                    Instant.parse(item);
                    return true;
                } catch (DateTimeParseException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should be an ISO date time string");
            }
        };
    }

    public static Matcher<String> matchesPattern(String regex) {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                return item != null && item.matches(regex);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should match regex: " + regex);
            }
        };
    }
}