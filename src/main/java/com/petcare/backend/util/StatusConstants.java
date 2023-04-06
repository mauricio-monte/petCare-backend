package com.petcare.backend.util;

public class StatusConstants {
    public static final String USER_CREATED = "user_created";
    public static final String LOGIN_SUCCESS = "login_success";

    // ERROR STATUSES
    public static final String LOGIN_FAILED_STATUS = "login_failed";
    public static final String EMAIL_ALREADY_REGISTERED_STATUS = "email_already_registered";
    public static final String USERNAME_ALREADY_REGISTERED_STATUS = "username_already_registered";

    public static final String USER_NOT_FOUND_STATUS = "user_not_found";
    public static final String PET_NOT_FOUND_STATUS = "pet_not_found";
    public static final String VACCINE_NOT_FOUND_STATUS = "vaccine_not_found";
    public static final String DOSE_NOT_FOUND_STATUS = "dose_not_found";
    public static final String PHOTO_NOT_FOUND_STATUS = "photo_not_found";
    public static final String EXAM_NOT_FOUND_STATUS = "exam_not_found";
    public static final String EXAM_FILE_NOT_FOUND_STATUS = "exam_file_not_found";
    public static final String INTERNAL_IO_ERROR_STATUS = "internal_io_operation_error";
}
