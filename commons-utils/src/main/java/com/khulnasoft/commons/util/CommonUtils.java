package com.khulnasoft.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Common utility methods for the KhulnaSoft DevLabs ecosystem.
 */
public class CommonUtils {

    private static final Random RANDOM = new Random();

    /**
     * Generates a random integer within the specified range.
     */
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random string of specified length.
     */
    public static String randomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random email address.
     */
    public static String randomEmail() {
        return randomString(8) + "@" + randomString(5) + ".com";
    }

    /**
     * Generates a random phone number.
     */
    public static String randomPhone() {
        return "+1" + randomInt(100, 999) + randomInt(1000, 9999);
    }

    /**
     * Formats a LocalDateTime to ISO string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Parses an ISO date time string.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Checks if a string is null or empty.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Safely converts a string to integer with default value.
     */
    public static int safeToInt(String str, int defaultValue) {
        if (isNullOrEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Safely converts a string to long with default value.
     */
    public static long safeToLong(String str, long defaultValue) {
        if (isNullOrEmpty(str)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Safely converts a string to double with default value.
     */
    public static double safeToDouble(String str, double defaultValue) {
        if (isNullOrEmpty(str)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
