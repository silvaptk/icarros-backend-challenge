package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;


/**
 * Validation rule that requires a given password to have a digit (1, 2, ...)
 */
@Component
public class DigitPasswordValidationRule extends PatternValidationRule {

    public DigitPasswordValidationRule(PasswordValidator validator) {
        super(validator, "[0-9]");
    }

}
