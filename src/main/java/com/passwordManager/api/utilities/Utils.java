package com.passwordManager.api.utilities;

import com.passwordManager.api.exceptions.InvalidDateInputException;

import java.time.LocalDate;

public class Utils {
    private static final int currentYear = LocalDate.now().getYear();
    public static int getExpirationMonth(int month) {
        if (month < 1 || month > 12) {
            throw new InvalidDateInputException(String.format("Invalid expiration month: %d",
                    month));
        }

        return month;
    }

    public static int getExpirationYear(int year) {
        if (year < currentYear) {
            throw new InvalidDateInputException(String.format("Expiration year cannot be in the " +
                    "past: %d",year));
        }
        return year;
    }
}
