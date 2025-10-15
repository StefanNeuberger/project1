package org.example;

import java.util.Set;

/**
 * Utility class for validating passwords.
 * <p>
 * Provides instance methods (enforced via PasswordValidatorSpec). All methods are null-safe.
 */
public final class PasswordValidator implements PasswordValidatorSpec {

    private static final int DEFAULT_MIN_LENGTH = 8;

    // Small internal list of common passwords
    private static final Set<String> COMMON_PASSWORDS = Set.of(
            "123456", "password", "123456789", "qwerty", "111111", "abc123",
            "password1", "12345678", "12345", "iloveyou"
    );

    // Default allowed special characters used by isValid
    private static final String DEFAULT_ALLOWED_SPECIALS = "!@#$%^&*()_+-=[]{}|;:'\"`,.<>/?~\\";

    @Override
    public boolean hasMinLength(String password) {
        if (password == null) return false;
        return password.length() >= DEFAULT_MIN_LENGTH;
    }

    @Override
    public boolean containsDigit(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {

            if (Character.isDigit(password.charAt(i))) return true;
        }
        return false;
    }

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

    @Override
    public boolean isCommonPassword(String password) {
        if (password == null) return false;
        // Compare case-insensitively against the small internal list (no stripping)
        String normalized = password.trim().toLowerCase();
        return COMMON_PASSWORDS.contains(normalized);
    }

    @Override
    public boolean containsSpecialChar(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {
            if (DEFAULT_ALLOWED_SPECIALS.indexOf(password.charAt(i)) >= 0) return true;
        }
        return false;
    }

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
