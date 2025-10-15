package org.example;

import java.util.Scanner;

public class Main {

    /**
     * Asks for user input from stdin and checks if the password is valid using the provided validator.
     * Prints the result to stdout.
     */
    public static void isValidPassport(PasswordValidator validator) {
        if (validator == null) {
            System.out.println("Invalid");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password: ");
        String password = scanner.hasNextLine() ? scanner.nextLine() : null;
        boolean valid = validator.isValid(password);
        System.out.println(valid ? "Valid" : "Invalid");
    }

    public static void main(String[] args) {

        final boolean needsUppercase = true;
        final boolean needsDigits = true;
        final boolean needsLowercase = true;
        final int minLength = 8;
        final String allowedSpecialChars = "!@#$%^&*()";

        PasswordValidator validator = new PasswordValidator(minLength, allowedSpecialChars, needsUppercase, needsDigits, needsLowercase);

        // Ask the user for a password and validate it
        isValidPassport(validator);
    }
}