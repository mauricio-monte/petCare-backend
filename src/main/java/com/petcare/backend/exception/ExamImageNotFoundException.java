package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class ExamImageNotFoundException extends Exception {

    public ExamImageNotFoundException() {
        super(StatusConstants.EXAM_IMAGE_NOT_FOUND_STATUS);
    }
}
