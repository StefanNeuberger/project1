package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;

public class PasswordGenerator {

    private final int minLength;
    private final String allowedSpecialChars;
    private final String allChars;
    private boolean needsUppercase = true;
    private boolean needsDigits = true;
    private boolean needsLowercase = true;


    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private final SecureRandom secureRandom = new SecureRandom();


    public PasswordGenerator(int minLength, String allowedSpecialChars, boolean needsUppercase, boolean needsDigits, boolean needsLowercase) {
        this.minLength = minLength;
        this.allowedSpecialChars = allowedSpecialChars;
        this.needsUppercase = needsUppercase;
        this.needsLowercase = needsLowercase;
        this.needsDigits = needsDigits;

        this.allChars = LOWERCASE + UPPERCASE + DIGITS + allowedSpecialChars;
    }

    public String generatePassword() {
        // ArrayList to store password characters
        ArrayList<Character> passwordChars = new ArrayList<>();

        // A. Add one character from each policy
        if (needsLowercase) {
            passwordChars.add(getRandomChar(LOWERCASE));
        }
        if (needsUppercase) {
            passwordChars.add(getRandomChar(UPPERCASE));

        }
        if (needsDigits) {
            passwordChars.add(getRandomChar(DIGITS));
        }

        if (!this.allowedSpecialChars.isEmpty()) {
            passwordChars.add(getRandomChar(this.allowedSpecialChars));
        }

        int missingCharsLength = minLength - passwordChars.size();

        // B. Add remaining characters from allChars
        for (int i = 0; i < missingCharsLength; i++) {
            passwordChars.add(getRandomChar(allChars));
        }

        StringBuilder passwordBuilder = new StringBuilder();
        for (Character c : passwordChars) {
            passwordBuilder.append(c);
        }

        return passwordBuilder.toString();
    }


    // Get random character from string at a random index
    private char getRandomChar(String chars) {
        int randomIndex = secureRandom.nextInt(chars.length());
        return chars.charAt(randomIndex);
    }

}
