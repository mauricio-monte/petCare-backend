package com.petcare.backend.util;

import java.time.Instant;
import java.util.Date;

public class Validator {

    private static final String INVALID_DATE = "invalid_date";
    private static final String INVALID_SEX = "invalid_sex";
    private static final String INVALID_WEIGHT = "invalid_weight";


    public static void validateIsTodayOrBefore(Date date) {
        if (date != null) {
            Date now = Date.from(Instant.now());
            if (date.after(now)) {
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
}
