package com.example.icarros_challenge.icarros_challenge.e2e;

import com.example.icarros_challenge.icarros_challenge.controller.exception.DefaultException;
import com.example.icarros_challenge.icarros_challenge.controller.exception.MethodNotSupportedException;
import com.example.icarros_challenge.icarros_challenge.controller.exception.ResourceNotFoundException;
import io.restassured.RestAssured;
import io.restassured.response.Validatable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("e2e")
public class ErrorHandlingTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testNonexistentResource() {
        ResourceNotFoundException exception = new ResourceNotFoundException();

        given()
                .contentType("text/plain")
                .body("abc")
            .when()
                .post("/does/not/exist")
            .then()
                .statusCode(404)
                .body("code", equalTo(exception.getCode()))
                .body("message", equalTo(exception.getMessage()));
    }

    @Test
    public void testWrongMethod() {
        MethodNotSupportedException exception = new MethodNotSupportedException();

        given()
                .contentType("text/plain")
                .body("abc")
                .when()
                .patch("/auth/password-validation")
                .then()
                .statusCode(405)
                .body("code", equalTo(exception.getCode()))
                .body("message", equalTo(exception.getMessage()));
    }

}
