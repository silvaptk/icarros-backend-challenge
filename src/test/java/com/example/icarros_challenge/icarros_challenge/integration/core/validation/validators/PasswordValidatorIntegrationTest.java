package com.example.icarros_challenge.icarros_challenge.integration.core.validation.validators;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.password.*;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@Tag("unit")
public class PasswordValidatorIntegrationTest {

    private PasswordValidator validator;

    @BeforeEach
    public void setup() {
        this.validator = new PasswordValidator();
        new DigitPasswordValidationRule(validator);
        new InvalidTypePasswordValidationRule(validator);
        new LowerLetterPasswordValidationRule(validator);
        new ShortPasswordValidationRule(validator);
        new SpecialCharPasswordValidationRule(validator);
        new UniqueCharsPasswordValidationRule(validator);
        new UpperLetterPasswordValidationRule(validator);
        new ValidCharsPasswordValidationRule(validator);
    }

    @Test
    public void testWithEmptyPassword() {
        Assertions.assertFalse(this.validator.validate(""));
    }

    @ParameterizedTest
    @CsvSource({ "aB1@", "abAB12@#" })
    public void testWithShortPassword(String givenPassword) {
        Assertions.assertFalse(this.validator.validate(givenPassword));
    }

    @Test
    public void testWithoutLowerLetters() {
        Assertions.assertFalse(this.validator.validate("ABC123!@#"));
    }

    @Test
    public void testWithoutUpperLetters() {
        Assertions.assertFalse(this.validator.validate("abc123!@#"));
    }

    @Test
    public void testWithoutNumbers() {
        Assertions.assertFalse(this.validator.validate("abcABC!@#"));
    }

    @ParameterizedTest
    @CsvSource({ "aaaABC123!@#", "abcAAA123!@#", "abcABC111!@#", "abcABC123!!!" })
    public void testWithRepetition(String givenPassword) {
        Assertions.assertFalse(this.validator.validate(givenPassword));
    }

    @Test
    public void testWithInvalidCharacters() {
        Assertions.assertFalse(this.validator.validate("abcABC123!@ "));
    }

    @Test
    public void testWithValidPassword() {
        Assertions.assertTrue(this.validator.validate("abcABC123!@#"));
    }

}
