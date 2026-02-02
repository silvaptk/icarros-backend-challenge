package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Validation rule that requires a password to have a special
 * character based on a list of valid ones
 */
@Component
public class SpecialCharPasswordValidationRule extends PatternValidationRule {

    private static final String ALLOWED_SPECIAL_CHARS = "!@#$%^&*()-+";

    public SpecialCharPasswordValidationRule(PasswordValidator validator) {
        super(validator, String.format("[%s]", ALLOWED_SPECIAL_CHARS));
    }

}
