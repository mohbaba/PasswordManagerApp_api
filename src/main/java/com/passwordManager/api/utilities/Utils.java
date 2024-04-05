package com.passwordManager.api.utilities;

import com.passwordManager.api.exceptions.InvalidDateInputException;

import java.time.LocalDate;

public class Utils {
    public static LocalDate getExpirationMonth(int month) {
        if (month < 1 || month > 12) {
            throw new InvalidDateInputException(String.format("Invalid expiration month: %d",
                    month));
        }
        return LocalDate.of(LocalDate.now().getYear(), month, 1); // Assuming expiration day is the first day of the month
    }

    public static LocalDate getExpirationYear(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new InvalidDateInputException(String.format("Expiration year cannot be in the " +
                    "past: %d",year));
        }
        return LocalDate.of(year, 1, 1); // Assuming expiration month and day are the first month and day of the year
    }
}
