package com.khulnasoft.khulnasoftjenkins.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utility for decoding console output to plain text
 */
public class PlainTextConsoleUtils {

    /**
     * Decode console output buffer to plain text
     * @param buffer the buffer containing console data
     * @param length the length of data to decode
     * @param output the output stream to write decoded text
     * @throws IOException if writing fails
     */
    public static void decodeConsole(byte[] buffer, int length, OutputStream output) throws IOException {
        if (buffer == null || length <= 0) {
            return;
        }

        // For this implementation, we'll assume the buffer contains UTF-8 text
        // In a real implementation, this might need to handle Jenkins' console encoding
        String text = new String(buffer, 0, length, StandardCharsets.UTF_8);

        // Basic cleanup - remove ANSI escape codes if present
        text = removeAnsiEscapeCodes(text);

        // Write the cleaned text
        output.write(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Remove ANSI escape codes from text
     * @param text the text to clean
     * @return text without ANSI escape codes
     */
    private static String removeAnsiEscapeCodes(String text) {
        if (text == null) {
            return null;
        }

        // Simple regex to remove ANSI escape sequences
        return text.replaceAll("\\x1B\\[[0-9;]*[mG]", "");
    }

    /**
     * Check if text contains console formatting
     * @param text the text to check
     * @return true if formatting is detected
     */
    public static boolean hasConsoleFormatting(String text) {
        if (text == null) {
            return false;
        }

        // Check for ANSI escape codes
        return text.contains("\u001B[") || text.contains("\\x1B[");
    }

    /**
     * Get plain text representation of console output
     * @param consoleText the console text
     * @return plain text version
     */
    public static String getPlainText(String consoleText) {
        if (consoleText == null) {
            return null;
        }

        return removeAnsiEscapeCodes(consoleText);
    }
}
