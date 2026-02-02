package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Validation rule that requires a password to have at least one uppercase letter
 */
@Component
public class UpperLetterPasswordValidationRule extends PatternValidationRule {

    public UpperLetterPasswordValidationRule(PasswordValidator validator) {
        super(validator, "[A-ZÀ-ÖØ-Ý]");
    }

}
