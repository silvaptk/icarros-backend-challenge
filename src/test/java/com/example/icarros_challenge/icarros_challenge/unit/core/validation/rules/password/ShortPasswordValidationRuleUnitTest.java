package com.example.icarros_challenge.icarros_challenge.unit.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.password.ShortPasswordValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Tag("unit")
public class ShortPasswordValidationRuleUnitTest {

    private ShortPasswordValidationRule rule;

    @BeforeEach
    public void setup() {
        PasswordValidator validator = Mockito.mock(PasswordValidator.class);
        rule = new ShortPasswordValidationRule(validator);
    }

    @Test
    public void testEmptyPassword() {
        Assertions.assertFalse(rule.validate(""));
    }

    @Test
    public void testSingleCharPassword() {
        Assertions.assertFalse(rule.validate("1"));
    }

    @Test
    public void testAlmostIdealPassword() {
        Assertions.assertFalse(rule.validate("12345678"));
    }

}
