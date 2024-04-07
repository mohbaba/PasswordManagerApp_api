package com.passwordManager.api.utilities;

public class NumericCipher {
    public static final String DIGITS = "0123456789";
    public static String encrypt(int number) {
        int shiftKey = 4;
        String numberString = Integer.toString(number);
        return getString(shiftKey, numberString);
    }

    public static String encrypt(long number) {
        int shiftKey = 4;
        String numberString = Long.toString(number);
        return getString(shiftKey, numberString);
    }

    private static String getString(int shiftKey, String numberString) {
        StringBuilder encryptedNumber = new StringBuilder();
        for (int i = 0; i < numberString.length(); i++) {
            char originalChar = numberString.charAt(i);

            if (Character.isDigit(originalChar)) {
                char encryptedChar = encryptChar(originalChar, shiftKey);
                encryptedNumber.append(encryptedChar);
            } else {
                encryptedNumber.append(originalChar);
            }
        }

        return encryptedNumber.toString();
    }

    private static char encryptChar(char originalChar, int shiftKey) {
        int position = DIGITS.indexOf(originalChar);
        int shiftedPosition = (position + shiftKey) % DIGITS.length();
        return DIGITS.charAt(shiftedPosition);
    }

    public static String decrypt(String encryptedNumber) {
        int shiftKey = 4;
        StringBuilder decryptedNumberString = new StringBuilder();

        for (int i = 0; i < encryptedNumber.length(); i++) {
            char encryptedChar = encryptedNumber.charAt(i);

            if (Character.isDigit(encryptedChar)) {
                char decryptedChar = decryptChar(encryptedChar, shiftKey);
                decryptedNumberString.append(decryptedChar);
            } else {
                decryptedNumberString.append(encryptedChar);
            }
        }

        return decryptedNumberString.toString();
    }

    private static char decryptChar(char encryptedChar, int shiftKey) {
        int position = DIGITS.indexOf(encryptedChar);
        int shiftedPosition = (position - shiftKey + DIGITS.length()) % DIGITS.length();
        return DIGITS.charAt(shiftedPosition);
    }
}

