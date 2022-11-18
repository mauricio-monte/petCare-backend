package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super(StatusConstants.LOGIN_FAILED_STATUS);
    }

}
