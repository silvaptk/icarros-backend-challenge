package com.example.icarros_challenge.icarros_challenge.integration.core.validation.validators;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("integration")
public class ValidatorIntegrationTest {

    private List<String> executedRules;

    @BeforeEach
    public void setup() {
         this.executedRules = new ArrayList<>();
    }

    @Test
    public void testRuleRegistration() {
        Validator<Object> validator = new Validator<Object>() {};
        this.createDummyValidationRule(validator, "1", true, 1);
        this.createDummyValidationRule(validator, "2", true, 1);

        validator.validate("");

        Assertions.assertEquals(List.of("1", "2"), this.executedRules);
    }

    @Test
    public void testThatRulesAreExecutedUntilOneFails() {
        Validator<Object> validator = new Validator<Object>() {};
        this.createDummyValidationRule(validator, "1", true, 1);
        this.createDummyValidationRule(validator, "2", false, 1);
        this.createDummyValidationRule(validator, "3", true, 1);

        validator.validate("");

        Assertions.assertEquals(List.of("1", "2"), this.executedRules);
    }

    @Test
    public void testThatRulesAreExecutedInPriorityOrder() {
        Validator<Object> validator = new Validator<Object>() {};
        this.createDummyValidationRule(validator, "1", true, 1);
        this.createDummyValidationRule(validator, "2", true, 1);
        this.createDummyValidationRule(validator, "3", true, 0);
        this.createDummyValidationRule(validator, "4", true, 0);

        validator.validate("");

        Assertions.assertEquals(List.of("3", "4", "1", "2"), this.executedRules);
    }

    private void createDummyValidationRule(
            Validator<Object> validator, String ruleId, Boolean validationResult, Integer priority
    ) {
        new ValidationRule<Object>(validator, priority) {
            @Override
            public Boolean validate(Object object) {
                executedRules.add(ruleId);
                return validationResult;
            }
        };
    }

}
