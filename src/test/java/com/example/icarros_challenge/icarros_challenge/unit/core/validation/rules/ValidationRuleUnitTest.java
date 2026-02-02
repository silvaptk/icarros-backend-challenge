package com.example.icarros_challenge.icarros_challenge.unit.core.validation.rules;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Tag("unit")
public class ValidationRuleUnitTest {

    @Test
    public void testValidatorRegistration() {
        Validator<Object> validator = this.createValidatorMock();

        ValidationRule<Object> validationRule = this.createDummyRule(validator, 1);

        Mockito.verify(validator).registerRule(validationRule);
    }

    @Test
    public void testPriorityBasedSorting() {
        Validator<Object> validator = this.createValidatorMock();
        ValidationRule<Object> importantRule = this.createDummyRule(validator, 1);
        ValidationRule<Object> defaultRule = this.createDummyRule(validator, 2);

        Assertions.assertEquals(-1, importantRule.compareTo(defaultRule));
        Assertions.assertEquals(1, defaultRule.compareTo(importantRule));
    }

    private Validator<Object> createValidatorMock() {
        return Mockito.mock(Validator.class);
    }

    private ValidationRule<Object> createDummyRule(Validator<Object> validator, Integer priority) {
        return new ValidationRule<Object>(validator, priority) {
            @Override
            public Boolean validate(Object object) {
                return null;
            }
        };
    }

}
