package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Validation rule that rejects passwords with repeated characters
 */
@Component
public class UniqueCharsPasswordValidationRule extends ValidationRule<String> {

    public UniqueCharsPasswordValidationRule(PasswordValidator validator) {
        super(validator);
    }

    @Override
    public Boolean validate(String password) {
        return password.chars().distinct().count() == password.length();
    }
}
