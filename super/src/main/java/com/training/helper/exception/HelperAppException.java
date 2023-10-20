package com.training.helper.exception;

public class HelperAppException extends RuntimeException{
    public HelperAppException(String message) {
        super(message);
    }

    public HelperAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelperAppException(Throwable cause) {
        super(cause);
    }
}
