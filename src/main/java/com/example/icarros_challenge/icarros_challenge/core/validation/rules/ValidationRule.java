package com.example.icarros_challenge.icarros_challenge.core.validation.rules;

import com.example.icarros_challenge.icarros_challenge.core.validation.validators.Validator;

/**
 * Class that abstracts a validation rule. Validation rules are the
 * essential pieces of the validation system. Each rule has a
 * priority level that defaults to 1. The rules are executed
 * following a priority-level based order. The rule with a priority
 * level of 1 will be executed before that a rule with a priority
 * level of 2
 * @param <ValidatingSubject> Class of the piece of data to be validated
 */
public abstract class ValidationRule<ValidatingSubject>  implements Comparable<ValidationRule<ValidatingSubject>> {

    private Integer priority = 1;

    /**
     * This constructor will create a rule with priority of 1
     * @param validator the validator that will apply the rule
     */
    public ValidationRule(Validator<ValidatingSubject> validator) {
        validator.registerRule(this);
    }

    /**
     * @param validator the validator that will apply the rule
     * @param priority the priority of the rule
     */
    public ValidationRule(Validator<ValidatingSubject> validator, Integer priority) {
        this.priority = priority;
        validator.registerRule(this);
    }

    /**
     * This method will be implemented by the subclasses to validate the
     * given piece of data
     * @param validatingSubject the piece of data to validate
     * @return if the piece of data is valid
     */
    public abstract Boolean validate(ValidatingSubject validatingSubject);

    /**
     * The priority of the rule. Defaults to 1. Rules with lower priority
     * will be validated first
     * @return rule priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Method implemented to allow the rules to be sorted according to their
     * priority
     * @param anotherRule the object to be compared.
     * @return the difference between rules priorities
     */
    @Override
    public int compareTo(ValidationRule<ValidatingSubject> anotherRule) {
        return this.getPriority() - anotherRule.getPriority();
    }
}
