package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Validation rule that requires a password to have a minimal amount
 * of characters
 */
@Component
public class ShortPasswordValidationRule extends ValidationRule<String> {

    private static int MIN_PASSWORD_LENGTH = 9;

    public ShortPasswordValidationRule(PasswordValidator validator) {
        super(validator);
    }

    @Override
    public Boolean validate(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

}
