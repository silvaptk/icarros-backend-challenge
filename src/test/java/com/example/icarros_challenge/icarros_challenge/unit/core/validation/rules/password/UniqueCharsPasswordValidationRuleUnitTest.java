package com.example.icarros_challenge.icarros_challenge.unit.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.password.UniqueCharsPasswordValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

@Tag("unit")
public class UniqueCharsPasswordValidationRuleUnitTest {

    private UniqueCharsPasswordValidationRule rule;

    @BeforeEach
    public void setup() {
        PasswordValidator validator = Mockito.mock(PasswordValidator.class);
        this.rule = new UniqueCharsPasswordValidationRule(validator);
    }

    @ParameterizedTest
    @CsvSource({ "aa", "aaa", "aaaa", "aaaaa" })
    public void testJustRepetition(String givenPassword) {
        Assertions.assertFalse(this.rule.validate(givenPassword));
    }

    @ParameterizedTest
    @CsvSource({ "aab", "baaac", "bacad" })
    public void testRepetitionWithDistinctChars(String givenPassword) {
        Assertions.assertFalse(this.rule.validate(givenPassword));
    }

    @ParameterizedTest
    @CsvSource({ "a", "ab", "abc", "1", "12", "123", "!", "!@", "!@#", "abcABC123!@#" })
    public void testWithoutRepetition() {
        Assertions.assertTrue(this.rule.validate("abc"));
    }

}
