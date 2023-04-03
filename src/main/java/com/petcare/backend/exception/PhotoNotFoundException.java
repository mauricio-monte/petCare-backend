package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class PhotoNotFoundException extends Throwable {

    public PhotoNotFoundException() {
        super(StatusConstants.PHOTO_NOT_FOUND_STATUS);
    }
}
