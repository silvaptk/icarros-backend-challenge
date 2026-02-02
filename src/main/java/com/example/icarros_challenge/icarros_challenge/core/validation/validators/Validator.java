package com.example.icarros_challenge.icarros_challenge.core.validation.validators;

import com.example.icarros_challenge.icarros_challenge.core.validation.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * One of the basis of the validation system. This class is
 * responsible for applying the validation rules to validate a given
 * piece of data.
 * @param <ValidatingSubject>
 */
public abstract class Validator<ValidatingSubject> {

    private final List<ValidationRule<ValidatingSubject>> registeredRules = new ArrayList<>();

    /**
     * Applies all the registered rules for this validator and returns
     * {@code true} if all the rules are satisfied, {@code false} when
     * some rule is not
     * @param validatingSubject the piece of data to validate
     * @return {@code true} if the data is valid according to the
     * registered rules, {@code false} otherwise
     */
    public Boolean validate(ValidatingSubject validatingSubject) {
        for (ValidationRule<ValidatingSubject> rule : registeredRules) {
            if (!rule.validate(validatingSubject)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Register a given rule and them sort the registered rules based
     * on their priority. The rule with the lowest priority will be
     * executed first.
     * @param rule The validation rule to be registered and
     *             thereafter executed to validate pieces of data
     */
    public void registerRule(ValidationRule<ValidatingSubject> rule) {
        this.registeredRules.add(rule);
        this.registeredRules.sort(null);
    }

}
