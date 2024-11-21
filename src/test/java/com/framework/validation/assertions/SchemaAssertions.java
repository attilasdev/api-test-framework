package com.framework.validation.assertions;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;

public class SchemaAssertions extends AbstractAssert<SchemaAssertions, Response> {
    
    private SchemaAssertions(Response response) {
        super(response, SchemaAssertions.class);
    }

    public static SchemaAssertions assertThat(Response response) {
        return new SchemaAssertions(response);
    }

    public SchemaAssertions matchesSchema(String schemaPath) {
        isNotNull();
        try {
            actual.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(
                    getClass().getResourceAsStream(schemaPath)));
            return this;
        } catch (Exception e) {
            failWithMessage("Response does not match schema: %s", e.getMessage());
            return null;
        }
    }
}