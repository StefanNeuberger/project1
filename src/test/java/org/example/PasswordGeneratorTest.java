package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordGeneratorTest {

    private static final int MIN_LENGTH = 12;
    private static final String ALLOWED_SPECIALS = "!@#$%^&*()";
    private static final boolean NEEDS_UPPERCASE = true;
    private static final boolean NEEDS_DIGITS = true;
    private static final boolean NEEDS_LOWERCASE = true;

    private final PasswordGenerator generator = new PasswordGenerator(MIN_LENGTH, ALLOWED_SPECIALS, NEEDS_UPPERCASE, NEEDS_DIGITS, NEEDS_LOWERCASE);

    // Helpers
    private boolean hasLower(String s) {
        return s.chars().anyMatch(Character::isLowerCase);
    }

    private boolean hasUpper(String s) {
        return s.chars().anyMatch(Character::isUpperCase);
    }

    private boolean hasDigit(String s) {
        return s.chars().anyMatch(Character::isDigit);
    }

    private boolean hasAllowedSpecial(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (ALLOWED_SPECIALS.indexOf(s.charAt(i)) >= 0) return true;
        }
        return false;
    }

    @Test
    @DisplayName("generatePassword: meets minimum length and policy requirements")
    void generatePassword_meetsPolicies() {
        String pw = generator.generatePassword();
        assertNotNull(pw);
        assertTrue(pw.length() >= MIN_LENGTH, "Password should have at least min length");
        assertTrue(hasLower(pw), "Should contain at least one lowercase");
        assertTrue(hasUpper(pw), "Should contain at least one uppercase");
        assertTrue(hasDigit(pw), "Should contain at least one digit");
        assertTrue(hasAllowedSpecial(pw), "Should contain at least one allowed special char");
    }


}