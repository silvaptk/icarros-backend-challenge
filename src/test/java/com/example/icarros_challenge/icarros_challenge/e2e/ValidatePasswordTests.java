package com.example.icarros_challenge.icarros_challenge.e2e;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.icarros_challenge.icarros_challenge.exception.InvalidBodyException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordIsMissingException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordTooShortException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithRepeatedCharactersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithUnexpectedCharacterException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutLowercaseLettersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutNumbersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutSpecialCharactersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutUppercaseLettersException;
import com.example.icarros_challenge.icarros_challenge.exception.ValidationException;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Tag("e2e")
public class ValidatePasswordTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testWithNoBody() {
        String jsonEncodedPassword = "";

        ValidationException expectedException = new InvalidBodyException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testNullPassword() {
        String jsonEncodedPassword = "{}";

        ValidationException expectedException = new PasswordIsMissingException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testEmptyPassword() {
        String jsonEncodedPassword = this.encodePasswordAsJson("");

        ValidationException expectedException = new PasswordTooShortException(9);

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testSmallPassword() {
        String jsonEncodedPassword = this.encodePasswordAsJson("12345678");

        ValidationException expectedException = new PasswordTooShortException(9);

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testPasswordWithoutLowercaseLetters() {
        String jsonEncodedPassword = this.encodePasswordAsJson("123456789");

        ValidationException expectedException = new PasswordWithoutLowercaseLettersException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testPasswordWithoutUppercaseLetters() {
        String jsonEncodedPassword = this.encodePasswordAsJson("12345678a");

        ValidationException expectedException = new PasswordWithoutUppercaseLettersException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testPasswordWithoutNumbers() {
        String jsonEncodedPassword = this.encodePasswordAsJson("aBcDeFgHi");

        ValidationException expectedException = new PasswordWithoutNumbersException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testPasswordWithoutSpecialCharacters() {
        String jsonEncodedPassword = this.encodePasswordAsJson("1BcDeFgHi");

        ValidationException expectedException = new PasswordWithoutSpecialCharactersException();

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    @Test
    public void testPasswordWithRepeatedCharacters() {
        List<String> passwordsToTest = List.of("111abcABC!@#", "123aaaABC!@#", "123abcAAA!@#", "123abcABC!!!");

        for (String password : passwordsToTest) {
            String jsonEncodedPassword = this.encodePasswordAsJson(password);

            ValidationException expectedException = new PasswordWithRepeatedCharactersException();

            this.testValidationException(jsonEncodedPassword, expectedException);
        }
    }

    @Test
    public void testPasswordWithUnknownCharacters() {
        String jsonEncodedPassword = this.encodePasswordAsJson("123abcABC!@#[{");

        ValidationException expectedException = new PasswordWithUnexpectedCharacterException("[,{");

        this.testValidationException(jsonEncodedPassword, expectedException);
    }

    private void testValidationException(String body, ValidationException exception) {
        RequestSpecification request = given();

        if (body == null) {
            request.contentType("text/plain");
        } else {
            request.contentType("application/json").body(body);
        }

        request
                .when()
                .post("/auth/validate-password")
                .then()
                .statusCode(400)
                .body("code", equalTo(exception.getCode()))
                .body("message", equalTo(exception.getMessage()));
    }

    private String encodePasswordAsJson(String password) {

        String json = """
        {
            "password": "#password"
        }
        """;

        return json.replace("#password", password);
    }
}
