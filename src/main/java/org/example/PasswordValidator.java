package org.example;

/**
 * Utility class for validating passwords.
 * <p>
 * Provides instance methods (enforced via PasswordValidatorSpec) and a default
 * singleton instance for convenience. All methods are null-safe.
 */
public final class PasswordValidator implements PasswordValidatorSpec {


    @Override
    public boolean hasMinLength(String password, int min) {
        return false;
    }

    @Override
    public boolean containsDigit(String password) {
        return false;
    }

    @Override
    public boolean containsUpperAndLower(String password) {
        return false;
    }

    @Override
    public boolean isCommonPassword(String password) {
        return false;
    }

    @Override
    public boolean containsSpecialChar(String password, String allowed) {
        return false;
    }

    @Override
    public boolean isValid(String password) {
        return false;
    }
}
