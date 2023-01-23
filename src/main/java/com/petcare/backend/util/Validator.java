package com.petcare.backend.util;

import java.time.Instant;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

    public static final String INVALID_DATE = "invalid_date";
    public static final String INVALID_SEX = "invalid_sex";
    public static final String INVALID_WEIGHT = "invalid_weight";
    public static final String NOT_NULL_RESTRICTION = "not_null_restriction";
    public static final String NOT_EMPTY_RESTRICTION = "not_empty_restriction";
    public static final String INVALID_EMAIL = "invalid_email_restriction";


    public static void validateIsTodayOrBefore(LocalDate date) {
        if (date != null) {
            LocalDate now = LocalDate.from(Instant.now());
            if (date.isAfter(now)) {
                throw new RuntimeException(INVALID_DATE);
            }
        }
    }

    public static void validateCharIsMOrF(boolean isUpdate, Character sex) {
        if (isUpdate && sex == null) {
            return;
        }
        String gender = String.valueOf(sex).toLowerCase();
        if (!gender.equals("m") && !gender.equals("f")) {
            throw new RuntimeException(INVALID_SEX);
        }
    }

    public static void validateWeight(Float n) {
        if (n != null && n <= 0) {
            throw new RuntimeException(INVALID_WEIGHT);
        }
    }

    public static void validateNotNull(Object o) {
        if (o == null) {
            throw new RuntimeException(NOT_NULL_RESTRICTION);
        }
    }

    public static void validateNotEmpty(String s) {
        if (s == null || s.isEmpty()) {
            throw new RuntimeException(NOT_EMPTY_RESTRICTION);
        }
    }

    public static void validateEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            throw new RuntimeException(INVALID_EMAIL);
        }
    }
}
