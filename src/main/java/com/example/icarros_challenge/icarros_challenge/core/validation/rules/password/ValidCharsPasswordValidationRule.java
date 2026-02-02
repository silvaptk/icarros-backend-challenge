package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Validation rule that only approves passwords such that all
 * characters are among a set of valid ones
 */
@Component
public class ValidCharsPasswordValidationRule extends PatternValidationRule {

    private static final String VALID_CHARS = String.join("", List.of(
            "A-ZÀ-ÖØ-Ý",        // Uppercase
            "a-zà-öø-ÿ",        // Lowercase
            "!@#$%^&*()\\-+",   // Special chars
            "0-9"               // Digits
    ));

    public ValidCharsPasswordValidationRule(PasswordValidator validator) {
        super(validator, String.format("^[%s]+$", VALID_CHARS));
    }

}
