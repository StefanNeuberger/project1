package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordValidatorTest {

    final int minLength = 8;
    final String allowedSpecialChars = "!@#$%^&*()";
    final boolean needsUppercase = true;
    final boolean needsDigits = true;
    final boolean needsLowercase = true;

    private final PasswordValidator validator = new PasswordValidator(minLength, allowedSpecialChars, needsUppercase, needsDigits, needsLowercase);

    // hasMinLength
    @ParameterizedTest
    @DisplayName("hasMinLength: returns true for length >= 8")
    @ValueSource(strings = {"TESTINGT", "Testing-123"})
    void hasMinLength_returnsTrue_whenLengthAtLeast8(String input) {
        assertTrue(validator.hasMinLength(input));
    }

    @ParameterizedTest
    @DisplayName("hasMinLength: returns false for length < 8")
    @ValueSource(strings = {"short", "1234567"})
    void hasMinLength_returnsFalse_whenTooShort(String input) {
        assertFalse(validator.hasMinLength(input));
    }

    @ParameterizedTest
    @DisplayName("hasMinLength: returns false for null and empty")
    @NullAndEmptySource
    void hasMinLength_returnsFalse_whenCalledWithNullOrEmptyString(String input) {
        assertFalse(validator.hasMinLength(input));
    }

    // containsDigit
    @ParameterizedTest
    @DisplayName("containsDigit: detects presence of digit")
    @ValueSource(strings = {"a1", "0abc", "abc9xyz"})
    void containsDigit_true_cases(String input) {
        assertTrue(validator.containsDigit(input));
    }

    @ParameterizedTest
    @DisplayName("containsDigit: returns false when no digits")
    @NullAndEmptySource
    @ValueSource(strings = {"abcXYZ!"})
    void containsDigit_false_cases(String input) {
        assertFalse(validator.containsDigit(input));
    }

    // containsUpperAndLower
    @ParameterizedTest
    @DisplayName("containsUpperAndLower: true when both present")
    @ValueSource(strings = {"Ab", "PassWord", "aB1"})
    void containsUpperAndLower_true_cases(String input) {
        assertTrue(validator.containsUpperAndLower(input));
    }

    @ParameterizedTest
    @DisplayName("containsUpperAndLower: false when only one case or none")
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "ABC", "1234"})
    void containsUpperAndLower_false_cases(String input) {
        assertFalse(validator.containsUpperAndLower(input));
    }

    // isCommonPassword
    @ParameterizedTest
    @DisplayName("isCommonPassword: true for known common entries (case-insensitive)")
    @ValueSource(strings = {"password", "PASSWORD", "123456"})
    void isCommonPassword_true_cases(String input) {
        assertTrue(validator.isCommonPassword(input));
    }

    @ParameterizedTest
    @DisplayName("isCommonPassword: false for uncommon or null")
    @NullSource
    @ValueSource(strings = {"notcommon123!"})
    void isCommonPassword_false_cases(String input) {
        assertFalse(validator.isCommonPassword(input));
    }


    // containsSpecialChar
    @ParameterizedTest
    @DisplayName("containsSpecialChar: true when default allowed special present")
    @ValueSource(strings = {"abc$def", "x@y", "test#case"})
    void containsSpecialChar_true_cases(String input) {
        assertTrue(validator.containsSpecialChar(input));
    }

    @ParameterizedTest
    @DisplayName("containsSpecialChar: false when no default specials present or bad inputs")
    @NullAndEmptySource
    @ValueSource(strings = {"abcdef"})
    void containsSpecialChar_ReturnFalse_WhenCalledWithFalseCases(String input) {
        assertFalse(validator.containsSpecialChar(input));
    }

    // isValid
    @Test
    @DisplayName("isValid: true when all conditions met")
    void isValid_ReturnsTrue_WhenCalledWithTrueCases() {
        // >=8, has digit, has upper and lower, has special from default set, not common
        assertTrue(validator.isValid("GoodPass1!"));
        assertTrue(validator.isValid("Aa1$aaaa"));
    }

    @ParameterizedTest
    @DisplayName("isValid: false when failing individual conditions")
    @ValueSource(strings = {"short1!", "NoDigits!!", "UPPERCASE1!", "NoSpecial1"})
    void isValid_ReturnsFalse_WhenCalledWithFalseCases() {
        assertFalse(validator.isValid(null)); // null
        assertFalse(validator.isValid("Short1!")); // too short (<8)
        assertFalse(validator.isValid("NoDigits!!")); // no digit
        assertFalse(validator.isValid("lowercase1!")); // no upper
        assertFalse(validator.isValid("UPPERCASE1!")); // no lower
        assertFalse(validator.isValid("NoSpecial1")); // no special

    }

    @Test
    void isValid_ReturnsFalse_WhenCalledWithNull() {
        assertFalse(validator.isValid(null));
    }

    @Test
    @DisplayName("generatePassword isValid: true when generated password isValid")
    void generatePassword_isValid_WhenCheckedAgainstValidator() {
        String pw = validator.generateValidPassword();
        assertTrue(validator.isValid(pw));
    }


}