package com.training.helper.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class FOR global exception
 *
 * @Author Naveen Raja
 */
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
    public ResponseEntity<HelperAppResponse> handleHelperException(HelperAppException exc){
        HelperAppResponse helperAppResponse=new HelperAppResponse();
        helperAppResponse.setMessage(exc.getMessage());
        helperAppResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        long yourMilliSeconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourMilliSeconds);
        helperAppResponse.setTimeStamp(sdf.format(resultdate));

        return new ResponseEntity<>(helperAppResponse,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<HelperAppResponse> HandleException(Exception e){
        HelperAppResponse helperAppResponse=new HelperAppResponse();
        helperAppResponse.setMessage(e.getMessage());
        helperAppResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        long yourMilliSeconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourMilliSeconds);
        helperAppResponse.setTimeStamp(sdf.format(resultdate));
        return new ResponseEntity<>(helperAppResponse,HttpStatus.BAD_REQUEST);
    }
}
