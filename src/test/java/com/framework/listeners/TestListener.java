package com.framework.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.util.Optional;

public class TestListener implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.printf("Test passed: %s%n", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.printf("Test failed: %s with exception: %s%n", 
            context.getDisplayName(), cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.printf("Test disabled: %s with reason: %s%n", 
            context.getDisplayName(), reason.orElse("No reason provided"));
    }
}