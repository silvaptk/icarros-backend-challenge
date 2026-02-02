package com.example.icarros_challenge.icarros_challenge.e2e;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("e2e")
public class PasswordValidationTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testWithEmptyPassword() {
        this.triggerRequestAndAssertAboutResponse("", false);
    }

    @ParameterizedTest
    @CsvSource({ "aB1@", "abAB12@#" })
    public void testWithShortPassword(String givenPassword) {
        this.triggerRequestAndAssertAboutResponse(givenPassword, false);
    }

    @Test
    public void testWithoutLowerLetters() {
        this.triggerRequestAndAssertAboutResponse("ABC123!@#", false);
    }

    @Test
    public void testWithoutUpperLetters() {
        this.triggerRequestAndAssertAboutResponse("abc123!@#", false);
    }

    @Test
    public void testWithoutNumbers() {
        this.triggerRequestAndAssertAboutResponse("abcABC!@#", false);
    }

    @ParameterizedTest
    @CsvSource({ "aaaABC123!@#", "abcAAA123!@#", "abcABC111!@#", "abcABC123!!!" })
    public void testWithRepetition(String givenPassword) {
        this.triggerRequestAndAssertAboutResponse(givenPassword, false);
    }

    @Test
    public void testWithInvalidCharacters() {
        this.triggerRequestAndAssertAboutResponse("abcABC123!@ ", false);
    }

    @Test
    public void testWithValidPassword() {
        this.triggerRequestAndAssertAboutResponse("abcABC123!@#", true);
    }

    private void triggerRequestAndAssertAboutResponse(String body, Boolean expectedResponseBody) {
        given()
                .contentType("text/plain")
                .body(body)
            .when()
                .post("/auth/password-validation")
            .then()
                .statusCode(200)
                .body(equalTo(expectedResponseBody.toString()));
    }
}
