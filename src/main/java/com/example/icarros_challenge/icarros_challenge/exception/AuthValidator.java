package com.example.icarros_challenge.icarros_challenge.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for validating auth-related data
 */
public class AuthValidator {

    private final static int MIN_PASSWORD_LENGTH = 9;
    private final static String SPECIAL_CHARACTERS = "!@#$%^&*()-+";

    /**
     * Method to validate a password. It will  throw a `ValidationException` 
     * if the password isn't valid
     * @param password
     * @return `true` indicating that the password is valid
     */
    public Boolean validatePassword(String password) {
        this.validatePasswordClass(password);
        this.validatePasswordLength(password);
        this.validatePasswordLowercaseLetters(password);
        this.validatePasswordUppercaseLetters(password);
        this.validatePasswordNumbers(password);
        this.validatePasswordSpecialCharacters(password);
        this.validatePasswordRepeatedCharacters(password);
        this.validatePasswordUnexpectedCharacters(password);

        return true;
    }

    /**
     * Validates the password class. This is needed because the 
     * client may have sent no password at all
     * @param password
     */
    private void validatePasswordClass(String password) {
        if (password == null || password.getClass() != String.class) {
            throw new PasswordIsMissingException();
        }
    }

    /**
     * Validates the password length, throwing if it's too 
     * short
     * @param password
     */
    private void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooShortException(MIN_PASSWORD_LENGTH);
        }
    }

    /**
     * Validates if the password has lowercase letters, throwing if 
     * not
     * @param password
     */
    private void validatePasswordLowercaseLetters(String password) {
        Pattern pattern = Pattern.compile("[a-z]");
        if (!pattern.matcher(password).find()) {
            throw new PasswordWithoutLowercaseLettersException();
        }
    }

    /**
     * Validates if the password has uppercase letters, throwing if 
     * not
     * @param password
     */
    private void validatePasswordUppercaseLetters(String password) {
        Pattern uppercaseLettersPattern = Pattern.compile("[A-Z]");
        if (!uppercaseLettersPattern.matcher(password).find()) {
            throw new PasswordWithoutUppercaseLettersException();
        }
    }

    /**
     * Validates if the password has numbers, throwing if not
     * @param password
     */
    private void validatePasswordNumbers(String password) {
        Pattern numbersPattern = Pattern.compile("\\d");
        if (!numbersPattern.matcher(password).find()) {
            throw new PasswordWithoutNumbersException();
        }
    }

    /**
     * Validates if the password has special characters, throwing if not
     * @param password
     */
    private void validatePasswordSpecialCharacters(String password) {
        Pattern specialCharactersPattern = Pattern.compile("[" + SPECIAL_CHARACTERS + "]");
        if (!specialCharactersPattern.matcher(password).find()) {
            throw new PasswordWithoutSpecialCharactersException();
        }
    }

    /**
     * Validates if the password has repeated characters, throwing if so
     * @param password
     */
    private void validatePasswordRepeatedCharacters(String password) {
        Pattern repeatedCharactersPattern = Pattern.compile("(.).*\\1");
        if (repeatedCharactersPattern.matcher(password).find()) {
            throw new PasswordWithRepeatedCharactersException();
        }
    }

    /**
     * Validates if the password has unexpected characters, throwing if so
     * @param password
     */
    private void validatePasswordUnexpectedCharacters(String password) {
        Matcher unexpectedCharacterMatcher = Pattern.compile("[^a-zA-Z0-9" + SPECIAL_CHARACTERS + "]").matcher(password);

        List<String> unexpectedCharacters = new ArrayList<>();

        while (unexpectedCharacterMatcher.find()) {
            unexpectedCharacters.add(unexpectedCharacterMatcher.group());
        }

        if (!unexpectedCharacters.isEmpty()) {
            throw new PasswordWithUnexpectedCharacterException(String.join(",", unexpectedCharacters));
        }
    }
    
}
