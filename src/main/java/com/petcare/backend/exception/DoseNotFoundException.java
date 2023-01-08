package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class DoseNotFoundException extends Exception {

    public DoseNotFoundException() {
        super(StatusConstants.DOSE_NOT_FOUND_STATUS);
    }
}
