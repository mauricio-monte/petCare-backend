package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class UsernameAlreadyRegisteredException extends Exception {

    public UsernameAlreadyRegisteredException() {
        super(StatusConstants.USERNAME_ALREADY_REGISTERED_STATUS);
    }
}