package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.validation.assertions.ResponseAssertions;
import com.framework.validation.verifier.ResponseVerifier;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static com.framework.validation.matchers.CustomMatchers.*;
import static com.framework.validation.matchers.ResponseMatchers.*;
import org.hamcrest.MatcherAssert;


public class GithubApiTest extends BaseTest {

    @Test
    void shouldValidateRepositoryResponse() {
        Response response = restClient.getRequestSpec()
            .when()
                .get("/repos/octocat/Hello-World")
            .then()
                .extract().response();

        ResponseVerifier.verify(response)
            .statusCode(200)
            .schema("schemas/repository-schema.json")
            .assertResponse(resp -> {
                ResponseAssertions.assertThat(resp)
                    .hasResponseTime(2000)
                    .hasHeader("Content-Type")
                    .hasHeaderValue("Content-Type", "application/json; charset=utf-8")
                    .hasJsonPath("name")
                    .hasJsonPath("owner.login");
                
                // Verify specific field formats
                String createdAt = resp.jsonPath().getString("created_at");
                org.hamcrest.MatcherAssert.assertThat(createdAt, isISODateTime());
            });
    }

    @Test
    void shouldValidateRepositoryResponseWithMatchers() {
        Response response = restClient.getRequestSpec()
            .when()
                .get("/repos/octocat/Hello-World")
            .then()
                .extract().response();

        // Using the custom matchers
        MatcherAssert.assertThat(response, hasJsonPath("name"));
        MatcherAssert.assertThat(response, hasResponseTimeBelow(2000));
        MatcherAssert.assertThat(response, hasContentType("application/json"));
        MatcherAssert.assertThat(response, hasHeader("X-GitHub-Media-Type"));
        MatcherAssert.assertThat(response, hasJsonPathWithValue("name", "Hello-World"));
        MatcherAssert.assertThat(response, hasNonEmptyBody());
    }
}
