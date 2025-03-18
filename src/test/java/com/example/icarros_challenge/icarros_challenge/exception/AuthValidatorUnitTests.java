package com.example.icarros_challenge.icarros_challenge.exception;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class AuthValidatorUnitTests {
    private AuthValidator validator; 

    @BeforeEach
    public void setup() {
        validator = new AuthValidator();
    }

    @Test
    public void testEmptyPassword() {
        try {
            validator.validatePassword("");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordTooShortException.class, exception.getClass());
        }
    }

    @Test
    public void testSmallPassword() {
        try {
            validator.validatePassword("12345678");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordTooShortException.class, exception.getClass());
        }
    }

    @Test
    public void testPasswordWithoutLowercaseLetters() {
        try {
            validator.validatePassword("123456789");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutLowercaseLettersException.class, exception.getClass());
        }
    }

    @Test
    public void testPasswordWithoutUppercaseLetters() {
        try {
            validator.validatePassword("12345678a");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutUppercaseLettersException.class, exception.getClass());
        }
    }

    @Test
    public void testPasswordWithoutNumbers() {
        try {
            validator.validatePassword("aBcDeFgHiJ");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutNumbersException.class, exception.getClass());
        }
    }

    @Test
    public void testPasswordWithoutSpecialCharacters() {
        try {
            validator.validatePassword("aBcDeFgHi1");
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutSpecialCharactersException.class, exception.getClass());
        }
    }

    @Test
    public void testPasswordWithRepeatedCharacters() {
        List<String> passwordsToTest = List.of("aacABC123!@#", "abcAAC123!@#", "abcABC113!@#", "abcABC123!!#");

        for (String password : passwordsToTest) {
            try {
                validator.validatePassword(password);
            } catch (RuntimeException exception) {
                Assertions.assertEquals(PasswordWithRepeatedCharactersException.class, exception.getClass());
            }
        }
    }

    @Test
    public void testValidPassword() {
        boolean result = validator.validatePassword("123abcABC!@#");

        Assertions.assertEquals(true, result);
    }
}
