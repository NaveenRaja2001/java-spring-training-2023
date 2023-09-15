package com.security.demoJWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TicketBookingExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<TicketBookingResponse> handleException(TicketBookingException exc){
        TicketBookingResponse ticketBookingResponse=new TicketBookingResponse();
        ticketBookingResponse.setMessage(exc.getMessage());
        ticketBookingResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        ticketBookingResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(ticketBookingResponse,HttpStatus.BAD_REQUEST);
    }
}
