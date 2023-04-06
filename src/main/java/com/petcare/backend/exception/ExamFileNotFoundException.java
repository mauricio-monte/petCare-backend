package com.petcare.backend.exception;

import com.petcare.backend.util.StatusConstants;

public class ExamFileNotFoundException extends Exception {

    public ExamFileNotFoundException() {
        super(StatusConstants.EXAM_FILE_NOT_FOUND_STATUS);
    }
}
