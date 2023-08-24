package com.training.meetingscheduler.exception;

public class MeetingScheduleException extends RuntimeException{
    public MeetingScheduleException(String message) {
        super(message);
    }

    public MeetingScheduleException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeetingScheduleException(Throwable cause) {
        super(cause);
    }
}
