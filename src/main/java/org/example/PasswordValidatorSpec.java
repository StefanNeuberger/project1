package org.example;

/**
 * Interface for password validation rules.
 */
public interface PasswordValidatorSpec {
    boolean hasMinLength(String password);

    boolean containsDigit(String password);

    boolean containsUpperAndLower(String password);

    boolean isCommonPassword(String password);

    boolean containsSpecialChar(String password);

    boolean isValid(String password);
}
