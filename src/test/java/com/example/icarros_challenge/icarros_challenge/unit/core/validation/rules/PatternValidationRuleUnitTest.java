package com.example.icarros_challenge.icarros_challenge.unit.core.validation.rules;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Tag("unit")
public class PatternValidationRuleUnitTest {

    @ParameterizedTest
    @CsvSource({ "[a-z],1", "[a-z],2", "[0-9],a", "[0-9],b" })
    public void testInvalidPasswords(String patternDefinition, String data) {
        Assertions.assertFalse(this.createRule(patternDefinition).validate(data));
    }

    @ParameterizedTest
    @CsvSource({ "[a-z],a", "[a-z],b", "[0-9],1", "[0-9],2" })
    public void testValidPasswords(String patternDefinition, String data) {
        Assertions.assertTrue(this.createRule(patternDefinition).validate(data));
    }

    private PatternValidationRule createRule(String patternDefinition) {
        Validator<String> validator = new Validator<String>() {};
        return new PatternValidationRule(validator, patternDefinition) {};
    }

}
