package com.training.meetingscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MeetingSchedulerExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<MeetingScheduleErrorResponse> handleException(MeetingScheduleException exc){
        MeetingScheduleErrorResponse meetingErrorResponse=new MeetingScheduleErrorResponse();
        meetingErrorResponse.setMessage(exc.getMessage());
        meetingErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        meetingErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(meetingErrorResponse,HttpStatus.BAD_REQUEST);
    }
}
