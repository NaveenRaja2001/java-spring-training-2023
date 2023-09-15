package com.security.demoJWT.exception;

public class TicketBookingException extends RuntimeException{
    public TicketBookingException(String message) {
        super(message);
    }

    public TicketBookingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketBookingException(Throwable cause) {
        super(cause);
    }
}
