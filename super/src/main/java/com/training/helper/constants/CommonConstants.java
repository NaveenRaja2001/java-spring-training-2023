package com.training.helper.constants;

public class CommonConstants {
    public static final String AUTHENTICATION_SUCCESSFULL_MESSSAGE="Authentication successfully";


    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_REQUESTED = "requested";
    public static final String STATUS_REJECTED = "rejected";

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String EMAIL_PATTERN_MESSAGE = "Please provide a valid email";
    public static final String REQUIRED_EMAIL="email is required";

    public static final String USERNAME_PATTERN_MESSAGE="Username must contain only letters ";
    public  static final String USERNAME_PATTERN="[a-zA-Z]{3,20}";
    public static final String REQUIRED_USERNAME="firstname is required";

    public static final String DOB_PATTERN_MESSAGE="DOB must be in dd/mm/yyyy format";
    public  static final String DOB_PATTERN="^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
    public static final String REQUIRED_DOB="DOB is required";

    public static final String REQUIRED_PASSWORD="Password is required";
    public static final String PASSWORD_PATTERN="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String PASSWORD_PATTERN_MESSAGE="Please provide a strong password";

    public static final String REQUIRED_GENDER="Gender is required";
    public static final String GENDER_PATTERN="^(?:male|female|other)$";
    public static final String GENDER_PATTERN_MESSAGE="male // female // other";

    public static final String TIMESLOT_NOT_FOUND="TimeSlot is empty";



    public static final long TIME_LIMIT = 3 * 60 * 60 * 1000;
}
