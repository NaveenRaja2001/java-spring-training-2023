package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HelperAppExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(ConstraintViolationException ex) {
        List<ValidationError> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ValidationError error = new ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
            errors.add(error);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HelperAppException.class)
    public ResponseEntity<HelperAppResponse> handleException(HelperAppException exc){
        HelperAppResponse ticketBookingResponse=new HelperAppResponse();
        ticketBookingResponse.setMessage(exc.getMessage());
        ticketBookingResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        ticketBookingResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(ticketBookingResponse,HttpStatus.BAD_REQUEST);
    }
}
