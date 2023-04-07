package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class ExamNotFoundException extends Exception {

    public ExamNotFoundException() {
        super(StatusConstants.EXAM_NOT_FOUND_STATUS);
    }
}
