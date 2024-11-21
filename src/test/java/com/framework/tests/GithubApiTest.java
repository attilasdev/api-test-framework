package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.model.Repository;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("GitHub API Tests")
@Feature("Repository Operations")
public class GithubApiTest extends BaseTest {

    @Test
    @Description("Verify getting repository information")
    void shouldGetRepositoryInformation() {
        // Enable logging using the correct method
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        System.out.println("Making request to GitHub API...");
        
        Repository repo = restClient.getRequestSpec()
            .when()
                .get("/repos/octocat/Hello-World")
            .then()
                .log().all()  // Log the response
                .statusCode(200)
                .extract().as(Repository.class);

        System.out.println("Repository name: " + repo.getName());
        System.out.println("Full name: " + repo.getFullName());

        assertThat(repo.getName()).isEqualTo("Hello-World");
        assertThat(repo.getFullName()).isEqualTo("octocat/Hello-World");
    }
}