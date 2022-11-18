package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super(StatusConstants.USER_NOT_FOUND_STATUS);
    }
}
