package org.example;

import java.util.Set;

/**
 * Utility class for validating passwords.
 * <p>
 * Provides instance methods (enforced via PasswordValidatorSpec). All methods are null-safe.
 */
public final class PasswordValidator implements PasswordValidatorSpec {

    private final boolean needsUppercase;
    private final boolean needsDigits;
    private final boolean needsLowercase;

    private final int minLength;
    private final String allowedSpecialChars;
    private final PasswordGenerator passwordGenerator;

    // Constructor
    public PasswordValidator(int minLength, String allowedSpecialChars, boolean needsUppercase, boolean needsDigits, boolean needsLowercase) {
        this.minLength = minLength;
        this.allowedSpecialChars = allowedSpecialChars;
        this.needsUppercase = needsUppercase;
        this.needsDigits = needsDigits;
        this.needsLowercase = needsLowercase;
        this.passwordGenerator = new PasswordGenerator(minLength, allowedSpecialChars, needsUppercase, needsDigits, needsLowercase);
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

        // Always enforce minimum length first
        if (!hasMinLength(password)) return false;

        // Conditionally enforce digit requirement
        if (this.needsDigits && !containsDigit(password)) return false;

        // Conditionally enforce upper/lower requirements
        if (this.needsUppercase && this.needsLowercase) {
            if (!containsUpperAndLower(password)) return false;
        } else if (this.needsUppercase) {
            if (!containsUppercase(password)) return false;
        } else if (this.needsLowercase) {
            if (!containsLowercase(password)) return false;
        }

        // Always require at least one allowed special character
        if (!containsSpecialChar(password)) return false;

        // Must not be a common password
        return !isCommonPassword(password);
    }

    // Internal helpers for individual case checks when policies are partially enabled
    private boolean containsUppercase(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) return true;
        }
        return false;
    }

    private boolean containsLowercase(String password) {
        if (password == null || password.isEmpty()) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) return true;
        }
        return false;
    }

    public String generateValidPassword() {
        return passwordGenerator.generatePassword();
    }

    // Expose the generator used by this validator (if needed by callers)
    public PasswordGenerator getPasswordGenerator() {
        return passwordGenerator;
    }

}
