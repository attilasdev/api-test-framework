# API Test Automation Framework

A modern test automation framework for REST APIs built with Java, REST Assured, and JUnit 5. This framework demonstrates industry best practices for API testing, including performance testing, security testing, and comprehensive validation capabilities.

## 🚀 Features

- **API Testing**
  - REST API testing with REST Assured
  - Custom validation framework
  - Response schema validation
  - Fluent assertions
  - Custom matchers

- **Advanced Testing Capabilities**
  - Performance testing with concurrent users
  - Security testing (XSS, SQL Injection, etc.)
  - Response time validation
  - Header validation
  - JSON path validation

- **Framework Features**
  - Modular and extensible architecture
  - Allure reporting integration
  - Parallel test execution
  - Retry mechanism for flaky tests
  - Comprehensive logging

## 🛠️ Tech Stack

- Java 17
- Maven
- REST Assured
- JUnit 5
- AssertJ
- Allure Reports
- WireMock
- Jackson (JSON processing)
- SLF4J & Logback (Logging)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

## 🔧 Setup

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/api-test-framework.git
cd api-test-framework
```

2. **Install dependencies**
```bash
mvn clean install
```

3. **Set up configuration (optional)**
- Copy `src/main/resources/config/example.properties` to `local.properties`
- Update values in `local.properties` with your settings

## 🏃‍♂️ Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=GithubApiTest
```

### Run with specific profile
```bash
mvn test -Pqa
```

### Generate Allure report
```bash
# Generate and open report
allure serve allure-results
```

## 📁 Project Structure

```
src/
├── main/java/com/framework/
│   ├── client/           # REST client implementation
│   │   └── RestClient.java
│   ├── config/           # Configuration management
│   │   └── ApiConfig.java
│   ├── model/            # Data models/POJOs
│   │   └── Repository.java
│   └── validation/       # Validation utilities
│       └── SchemaValidator.java
└── test/java/com/framework/
    ├── base/             # Base test setup
    │   └── BaseTest.java
    ├── performance/      # Performance tests
    │   └── PerformanceTest.java
    ├── security/         # Security tests
    │   └── SecurityTest.java
    ├── tests/            # API tests
    │   └── GithubApiTest.java
    └── validation/       # Test validators
        ├── assertions/
        ├── matchers/
        └── verifier/
```

## 🔍 Test Examples

### API Test
```java
@Test
void shouldGetRepositoryInformation() {
    Response response = restClient.getRequestSpec()
        .when()
            .get("/repos/octocat/Hello-World")
        .then()
            .statusCode(200)
            .extract().response();

    ResponseVerifier.verify(response)
        .statusCode(200)
        .schema("/schemas/repository-schema.json")
        .assertResponse(resp -> {
            ResponseAssertions.assertThat(resp)
                .hasResponseTime(2000)
                .hasHeader("Content-Type")
                .hasJsonPath("name");
        });
}
```

### Performance Test
```java
@Test
void loadTest() {
    PerformanceTest.withConcurrentUsers(50)
        .eachExecuting(10)
        .assertAverageResponseTime(500)
        .execute();
}
```

## 📊 Reporting

The framework uses Allure for reporting. After test execution, generate the report using:
```bash
allure serve allure-results
```

Reports include:
- Test execution results
- Response times
- Request/Response details
- Screenshots (if applicable)
- Environment information


## ✨ Acknowledgments

- [REST Assured](https://rest-assured.io/) for API testing capabilities
- [Allure Framework](https://docs.qameta.io/allure/) for reporting
- [GitHub API](https://docs.github.com/en/rest) for providing a test API

