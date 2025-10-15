package org.example;

import java.util.Set;

/**
 * Utility class for validating passwords.
 * <p>
 * Provides instance methods (enforced via PasswordValidatorSpec). All methods are null-safe.
 */
public final class PasswordValidator implements PasswordValidatorSpec {

    private final int minLength;
    private final String allowedSpecialChars;

    // Constructor
    public PasswordValidator(int minLength, String allowedSpecialChars) {
        this.minLength = minLength;
        this.allowedSpecialChars = allowedSpecialChars;
    }

    // Small internal list of common passwords
    private static final Set<String> COMMON_PASSWORDS = Set.of(
            "123456", "password", "123456789", "qwerty", "111111", "abc123",
            "password1", "12345678", "12345", "iloveyou"
    );


    @Override
    public boolean hasMinLength(String password) {
        if (password == null) return false;
        return password.length() >= this.minLength;
    }

    /**
     * Detects presence of digit in password.
     * returnValue: true if at least one digit is present, false otherwise.
     */
    @Override
    public boolean containsDigit(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {

            if (Character.isDigit(password.charAt(i))) return true;
        }
        return false;
    }

    /**
     * Detects presence of upper and lower case letters in password.
     * returnValue: true if at least one upper and lower case letter is present, false otherwise.
     */
    @Override
    public boolean containsUpperAndLower(String password) {
        if (password == null || password.isEmpty()) return false;
        boolean hasUpper = false;
        boolean hasLower = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            if (hasUpper && hasLower) return true;
        }
        return false;
    }

    /**
     * Detects presence of common password in password.
     * returnValue: true if password is in the small internal list, false otherwise.
     */
    @Override
    public boolean isCommonPassword(String password) {
        if (password == null) return false;
        // Compare case-insensitively against the small internal list (no stripping)
        String normalized = password.trim().toLowerCase();
        return COMMON_PASSWORDS.contains(normalized);
    }

    /**
     * Detects presence of special character in password.
     * returnValue: true if at least one special character is present, false otherwise.
     */
    @Override
    public boolean containsSpecialChar(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {
            if (allowedSpecialChars.indexOf(password.charAt(i)) >= 0) return true;
        }
        return false;
    }

    /**
     * Returns true if all conditions are met, false otherwise.
     */
    @Override
    public boolean isValid(String password) {
        if (password == null) return false;
        return hasMinLength(password)
                && containsDigit(password)
                && containsUpperAndLower(password)
                && containsSpecialChar(password)
                && !isCommonPassword(password);
    }
}
