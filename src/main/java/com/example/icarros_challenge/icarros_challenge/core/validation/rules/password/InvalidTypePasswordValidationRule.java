package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Validation rule that rejects passwords that are {@code null}
 */
@Component
public class InvalidTypePasswordValidationRule extends ValidationRule<String> {

    public InvalidTypePasswordValidationRule(PasswordValidator validator) {
        super(validator, 0);
    }

    @Override
    public Boolean validate(String password) {
        return (password != null);
    }
}
