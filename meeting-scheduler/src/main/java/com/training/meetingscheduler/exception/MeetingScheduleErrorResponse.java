package com.training.meetingscheduler.exception;

/**
 * Response bean for meetingScheduleError
 */
public class MeetingScheduleErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public MeetingScheduleErrorResponse(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "MeetingScheduleErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}

