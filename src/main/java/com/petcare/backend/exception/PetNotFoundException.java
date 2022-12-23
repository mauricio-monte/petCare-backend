package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class PetNotFoundException extends Exception {

    public PetNotFoundException() {
        super(StatusConstants.PET_NOT_FOUND_STATUS);
    }
}
