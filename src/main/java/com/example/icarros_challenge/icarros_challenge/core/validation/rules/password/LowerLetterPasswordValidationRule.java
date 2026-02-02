package com.example.icarros_challenge.icarros_challenge.core.validation.rules.password;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.PatternValidationRule;
import com.example.icarros_challenge.icarros_challenge.core.validation.validators.PasswordValidator;
import org.springframework.stereotype.Component;


/**
 * Validation rule that requires a password to have lowercase letters
 */
@Component
public class LowerLetterPasswordValidationRule extends PatternValidationRule {

    public LowerLetterPasswordValidationRule(PasswordValidator validator) {
        super(validator, "[a-zà-öø-ÿ]");
    }

}
