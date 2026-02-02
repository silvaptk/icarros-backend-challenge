package com.example.icarros_challenge.icarros_challenge.unit.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.password.InvalidTypePasswordValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

@Tag("unit")
public class InvalidTypePasswordValidationRuleUnitTest {

    private InvalidTypePasswordValidationRule rule;

    @BeforeEach
    public void setup() {
        PasswordValidator validator = Mockito.mock(PasswordValidator.class);
        rule = new InvalidTypePasswordValidationRule(validator);
    }

    @Test
    public void testWithNull() {
        Assertions.assertFalse(this.rule.validate(null));
    }

    @ParameterizedTest
    @CsvSource({ "a", "A", "1", "@", "aA1@", "abcABC123!@#" })
    public void testWithString(String givenPassword) {
        Assertions.assertTrue(this.rule.validate(givenPassword));
    }

}
