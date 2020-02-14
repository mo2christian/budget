package com.mo2christian.budget;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
public class LineResourceTest {

    @Inject
    @ConfigProperty(name = "app.key")
    private String apiKey;

    @Test
    @Order(1)
    public void add(){
        String json = "{\n" +
                "\t\"label\": \"Actions\",\n" +
                "\t\"amount\": 180,\n" +
                "\t\"type\": \"d\"\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .header("api-key", apiKey)
                .body(json)
                .post("/line")
                .then()
                .statusCode(is(200));
    }

    @Test
    @Order(2)
    public void testGetAll(){
        given()
                .when().get("/line")
                .then()
                .statusCode(is(200));
    }

}
