package com.framework.security;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import com.framework.security.config.SecurityConfig;

public class SecurityTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().port(SecurityConfig.PORT));
        wireMockServer.start();
        configureFor("localhost", SecurityConfig.PORT);

        // Setup mock endpoints
        stubFor(get(urlEqualTo("/api/secure"))
            .withHeader("Authorization", matching("Bearer .*"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{\"data\":\"secure content\"}")));

        stubFor(get(urlEqualTo("/api/secure"))
            .willReturn(aResponse()
                .withStatus(401)
                .withBody("{\"error\":\"unauthorized\"}")));
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testAuthenticationRequired() {
        // Test without auth token
        Response noAuthResponse = io.restassured.RestAssured.given()
            .baseUri(SecurityConfig.BASE_URL)
            .when()
            .get("/api/secure")
            .then()
            .extract()
            .response();

        assertThat(noAuthResponse.getStatusCode()).isEqualTo(401);

        // Test with invalid auth token
        Response invalidAuthResponse = io.restassured.RestAssured.given()
            .baseUri(SecurityConfig.BASE_URL)
            .header("Authorization", "Bearer invalid_token")
            .when()
            .get("/api/secure")
            .then()
            .extract()
            .response();

        assertThat(invalidAuthResponse.getStatusCode()).isEqualTo(401);
    }

    @Test
    void testXSSPrevention() {
        String xssPayload = "<script>alert('xss')</script>";
        
        Response response = io.restassured.RestAssured.given()
            .baseUri(SecurityConfig.BASE_URL)
            .queryParam("input", xssPayload)
            .when()
            .get("/api/secure")
            .then()
            .extract()
            .response();

        String responseBody = response.getBody().asString();
        assertThat(responseBody).doesNotContain("<script>");
    }

    @Test
    void testSQLInjectionPrevention() {
        String sqlInjectionPayload = "' OR '1'='1";
        
        Response response = io.restassured.RestAssured.given()
            .baseUri(SecurityConfig.BASE_URL)
            .queryParam("id", sqlInjectionPayload)
            .when()
            .get("/api/secure")
            .then()
            .extract()
            .response();

        assertThat(response.getStatusCode()).isEqualTo(401);
    }

    @Test
    void testSecureHeaders() {
        Response response = io.restassured.RestAssured.given()
            .baseUri(SecurityConfig.BASE_URL)
            .header("Authorization", "Bearer valid_token")
            .when()
            .get("/api/secure")
            .then()
            .extract()
            .response();

        // Verify security headers
        assertThat(response.getHeader("X-Content-Type-Options")).isEqualTo("nosniff");
        assertThat(response.getHeader("X-Frame-Options")).isEqualTo("DENY");
        assertThat(response.getHeader("X-XSS-Protection")).isEqualTo("1; mode=block");
    }
}