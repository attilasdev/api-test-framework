package com.framework.validation;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.InputStream;

public class SchemaValidator {
    public static void validateSchema(Response response, String schemaPath) {
        InputStream schema = SchemaValidator.class.getResourceAsStream(schemaPath);
        response.then().assertThat().body(matchesJsonSchema(schema));
    }
}