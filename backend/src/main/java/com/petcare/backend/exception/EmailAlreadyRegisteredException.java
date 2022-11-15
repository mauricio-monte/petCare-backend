package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class EmailAlreadyRegisteredException extends Exception {

    public EmailAlreadyRegisteredException() {
        super(StatusConstants.EMAIL_ALREADY_REGISTERED_STATUS);
    }
}