package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class VaccineNotFoundException extends Exception {

    public VaccineNotFoundException() {
        super(StatusConstants.VACCINE_NOT_FOUND_STATUS);
    }
}
