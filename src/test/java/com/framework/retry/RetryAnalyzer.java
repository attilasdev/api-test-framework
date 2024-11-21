package com.framework.retry;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class RetryAnalyzer implements TestExecutionExceptionHandler {
    private static final int MAX_RETRIES = 2;
    private int count = 0;

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (count < MAX_RETRIES) {
            count++;
            System.out.printf("Retrying test: %s, attempt: %d%n", 
                context.getTestMethod().get().getName(), count);
            return; // Retry the test
        }
        throw throwable; // Max retries reached, fail the test
    }
}