package com.example.icarros_challenge.icarros_challenge.core.validation.rules;

import com.example.icarros_challenge.icarros_challenge.core.validation.validators.Validator;

import java.util.regex.Pattern;

/**
 * This class simplifies implementation of rules that use regular
 * expressions to validate {@link String}s.
 */
public abstract class PatternValidationRule extends ValidationRule<String> {

    private final Pattern pattern;

    /**
     * @param validator that will apply this rule
     * @param patternDefinition regex to be used to validate the data
     */
    public PatternValidationRule(Validator<String> validator, String patternDefinition) {
        super(validator);
        this.pattern = Pattern.compile(patternDefinition);
    }

    /**
     * Validate the given {@link String} against a regular
     * expression
     * @param validatingSubject the piece of data to validate
     * @return Returns {@code true} if a match is found, {@code false}
     * otherwise
     */
    public Boolean validate(String validatingSubject) {
        return this.pattern.matcher(validatingSubject).find();
    }

}
