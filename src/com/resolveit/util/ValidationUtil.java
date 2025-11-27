package com.resolveit.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating user inputs
 * Provides methods to validate email, password, phone numbers, and text fields
 */
public class ValidationUtil {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10}$"
    );
    
    /**
     * Validates if email is in correct format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates if password meets minimum requirements
     * - At least 6 characters
     * - At least one letter
     * - At least one digit
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        
        boolean hasLetter = password.matches(".*[A-Za-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        
        return hasLetter && hasDigit;
    }
    
    /**
     * Validates if phone number is 10 digits
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validates if text is not empty and within length limit
     */
    public static boolean isValidText(String text, int minLength, int maxLength) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        int length = text.trim().length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Validates if username is alphanumeric with 3-20 characters
     */
    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9]{3,20}$");
    }
    
    /**
     * Trims and returns the input string, or empty string if null
     */
    public static String sanitizeInput(String input) {
        return input == null ? "" : input.trim();
    }
}
