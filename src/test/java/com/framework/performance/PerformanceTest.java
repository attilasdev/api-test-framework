package com.framework.performance;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import com.framework.performance.config.PerformanceConfig;

@Execution(ExecutionMode.CONCURRENT)
public class PerformanceTest {
    private static WireMockServer wireMockServer;
    private static final int CONCURRENT_USERS = 50;
    private static final int REQUESTS_PER_USER = 10;
    private static final long ACCEPTABLE_RESPONSE_TIME = 500; // ms

    @BeforeAll
    static void setup() {
        // Use port from config
        wireMockServer = new WireMockServer(wireMockConfig().port(PerformanceConfig.PORT));
        wireMockServer.start();
        WireMock.configureFor("localhost", PerformanceConfig.PORT);

        // Setup mock response
        stubFor(get(urlEqualTo("/api/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"message\":\"success\"}")));
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void loadTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        List<Long> responseTimes = new ArrayList<>();

        // Create concurrent users
        for (int user = 0; user < CONCURRENT_USERS; user++) {
            Thread thread = new Thread(() -> {
                for (int request = 0; request < REQUESTS_PER_USER; request++) {
                    long startTime = System.currentTimeMillis();
                    
                    Response response = io.restassured.RestAssured.given()
                        .baseUri(PerformanceConfig.BASE_URL)
                        .when()
                        .get("/api/test")
                        .then()
                        .extract()
                        .response();

                    long endTime = System.currentTimeMillis();
                    long responseTime = endTime - startTime;
                    
                    synchronized (responseTimes) {
                        responseTimes.add(responseTime);
                    }

                    assertThat(response.getStatusCode()).isEqualTo(200);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Calculate metrics
        double averageResponseTime = responseTimes.stream()
            .mapToLong(Long::valueOf)
            .average()
            .orElse(0.0);

        long maxResponseTime = responseTimes.stream()
            .mapToLong(Long::valueOf)
            .max()
            .orElse(0);

        // Assert performance criteria
        assertThat(averageResponseTime)
            .as("Average response time")
            .isLessThan(ACCEPTABLE_RESPONSE_TIME);

        System.out.printf("Performance Test Results:%n" +
            "Total Requests: %d%n" +
            "Average Response Time: %.2f ms%n" +
            "Max Response Time: %d ms%n",
            CONCURRENT_USERS * REQUESTS_PER_USER,
            averageResponseTime,
            maxResponseTime);
    }
}