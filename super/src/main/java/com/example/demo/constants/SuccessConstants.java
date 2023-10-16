package com.example.demo.constants;

public class SuccessConstants {
    public static final String AUTHENTICATION_SUCCESSFULL_MESSSAGE="Authentication successful";


    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_REQUESTED = "requested";

    public static final String STATUS_REJECTED = "rejected";



    public static final String NO_USER_REJECT_MESSAGE ="No users to reject";

    public static final String NO_USER_APPROVE_MESSAGE="No users to approve";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_HELPER = "HELPER";

    public static final String GENDER_PATTERN = "^(FEMALE|MALE)$";
    public static final String GENDER_PATTERN_MESSAGE = "Gender must be FEMALE or MALE";

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String EMAIL_PATTERN_MESSAGE = "Please provide a valid email";
    public static final String EMAIL_NOT_EMPTY_MESSAGE = "Email cannot be empty";
    public static final String USERNAME_PATTERN_MESSAGE="Username must contain only letters ";
    public  static final String USERNAME_PATTERN="[a-zA-Z]{3,20}";


    public static final long TIME_LIMIT = 3 * 60 * 60 * 1000;
}
