package com.example.icarros_challenge.icarros_challenge.application;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.icarros_challenge.icarros_challenge.dto.ValidatePasswordRequest;
import com.example.icarros_challenge.icarros_challenge.exception.AuthValidator;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordIsMissingException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordTooShortException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithRepeatedCharactersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutLowercaseLettersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutNumbersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutSpecialCharactersException;
import com.example.icarros_challenge.icarros_challenge.exception.PasswordWithoutUppercaseLettersException;

@Tag("integration")
public class AuthServiceIntegrationTests {

    private AuthService service;

    @BeforeEach
    public void setup() {
        AuthValidator validatorMock = mock(AuthValidator.class);
        this.customSetup(validatorMock);
    }

    public void customSetup(AuthValidator validator) {
        service = new AuthService(validator);
    }

    @Test
    public void testValidatorWithNullPassword() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest(null));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordIsMissingException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithEmptyPassword() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest(""));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordTooShortException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithSmallPassword() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest("12345678"));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordTooShortException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithPasswordWithoutLowercaseLetters() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest("123456789"));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutLowercaseLettersException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithPasswordWithoutUppercaseLetters() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest("12345678a"));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutUppercaseLettersException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithPasswordWithoutNumbers() {
        this.customSetup(new AuthValidator());

        try {
            service.validatePassword(new ValidatePasswordRequest("aBcDeFgHiJ"));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutNumbersException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithPasswordWithoutSpecialCharacters() {
        this.customSetup(new AuthValidator());
        
        try {
            service.validatePassword(new ValidatePasswordRequest("aBcDeFgHi1"));
        } catch (RuntimeException exception) {
            Assertions.assertEquals(PasswordWithoutSpecialCharactersException.class, exception.getClass());
        }
    }

    @Test
    public void testValidatorWithPasswordWithRepeatedCharacters() {
        this.customSetup(new AuthValidator());

        List<String> passwordsToTest = List.of("aacABC123!@#", "abcAAC123!@#", "abcABC113!@#", "abcABC123!!#");

        for (String password : passwordsToTest) {
            try {
                service.validatePassword(new ValidatePasswordRequest(password));
            } catch (RuntimeException exception) {
                Assertions.assertEquals(PasswordWithRepeatedCharactersException.class, exception.getClass());
            }
        }
    }

    @Test
    public void testValidatorWithValidPassword() {
        this.customSetup(new AuthValidator());

        boolean result = service.validatePassword(new ValidatePasswordRequest("123abcABC!@#"));

        Assertions.assertEquals(true, result);
    }
}
