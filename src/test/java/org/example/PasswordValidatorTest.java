package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    // hasMinLength
    @Test
    @DisplayName("hasMinLength: returns true for length >= 8")
    void hasMinLength_true_whenLengthAtLeast8() {
        assertTrue(validator.hasMinLength("TESTINGT")); // exactly 8
        assertTrue(validator.hasMinLength("Testing-123")); // > 8
    }

    @Test
    @DisplayName("hasMinLength: returns false for length < 8")
    void hasMinLength_false_whenTooShort() {
        assertFalse(validator.hasMinLength("short"));
        assertFalse(validator.hasMinLength("1234567"));
    }

    @Test
    @DisplayName("hasMinLength: returns false for null and empty")
    void hasMinLength_handlesNullAndEmpty() {
        assertFalse(validator.hasMinLength(null));
        assertFalse(validator.hasMinLength(""));
    }

    // containsDigit
    @Test
    @DisplayName("containsDigit: detects presence of digit")
    void containsDigit_true_cases() {
        assertTrue(validator.containsDigit("a1"));
        assertTrue(validator.containsDigit("0abc"));
        assertTrue(validator.containsDigit("abc9xyz"));
    }

    @Test
    @DisplayName("containsDigit: returns false when no digits")
    void containsDigit_false_cases() {
        assertFalse(validator.containsDigit("abcXYZ!"));
        assertFalse(validator.containsDigit(""));
        assertFalse(validator.containsDigit(null));
    }

    // containsUpperAndLower
    @Test
    @DisplayName("containsUpperAndLower: true when both present")
    void containsUpperAndLower_true_cases() {
        assertTrue(validator.containsUpperAndLower("Ab"));
        assertTrue(validator.containsUpperAndLower("PassWord"));
        assertTrue(validator.containsUpperAndLower("aB1"));
    }

    @Test
    @DisplayName("containsUpperAndLower: false when only one case or none")
    void containsUpperAndLower_false_cases() {
        assertFalse(validator.containsUpperAndLower("abc"));
        assertFalse(validator.containsUpperAndLower("ABC"));
        assertFalse(validator.containsUpperAndLower("1234"));
        assertFalse(validator.containsUpperAndLower(""));
        assertFalse(validator.containsUpperAndLower(null));
    }

    // isCommonPassword
    @Test
    @DisplayName("isCommonPassword: true for known common entries (case-insensitive)")
    void isCommonPassword_true_cases() {
        assertTrue(validator.isCommonPassword("password"));
        assertTrue(validator.isCommonPassword("PASSWORD"));
        assertTrue(validator.isCommonPassword("123456"));
    }

    @Test
    @DisplayName("isCommonPassword: false for uncommon or null")
    void isCommonPassword_false_cases() {
        assertFalse(validator.isCommonPassword("notcommon123!"));
        assertFalse(validator.isCommonPassword(null));
    }

    // containsSpecialChar
    @Test
    @DisplayName("containsSpecialChar: true when allowed special present")
    void containsSpecialChar_true_cases() {
        assertTrue(validator.containsSpecialChar("abc$def", "$"));
        assertTrue(validator.containsSpecialChar("abc$def", "$%"));
        assertTrue(validator.containsSpecialChar("x@y", "@#"));
    }

    @Test
    @DisplayName("containsSpecialChar: false when none of allowed specials present or bad inputs")
    void containsSpecialChar_false_cases() {
        assertFalse(validator.containsSpecialChar("abcdef", "$%"));
        assertFalse(validator.containsSpecialChar("abcdef", ""));
        assertFalse(validator.containsSpecialChar("abcdef", null));
        assertFalse(validator.containsSpecialChar("", "$%"));
        assertFalse(validator.containsSpecialChar(null, "$%"));
    }

    // isValid
    @Test
    @DisplayName("isValid: true when all conditions met")
    void isValid_true_case() {
        // >=8, has digit, has upper and lower, has special from default set, not common
        assertTrue(validator.isValid("GoodPass1!"));
        assertTrue(validator.isValid("Aa1$aaaa"));
    }

    @Test
    @DisplayName("isValid: false when failing individual conditions")
    void isValid_false_cases() {
        assertFalse(validator.isValid(null)); // null
        assertFalse(validator.isValid("Short1!")); // too short (<8)
        assertFalse(validator.isValid("NoDigits!!")); // no digit
        assertFalse(validator.isValid("lowercase1!")); // no upper
        assertFalse(validator.isValid("UPPERCASE1!")); // no lower
        assertFalse(validator.isValid("NoSpecial1")); // no special
        assertFalse(validator.isValid("Password1!")); // common base (password) considered common? 'password1' is in list
        assertFalse(validator.isValid("password1$")); // common password variant exact match 'password1' + different special not in list won't matter if not exact match; we keep one exact match to ensure false
    }
}