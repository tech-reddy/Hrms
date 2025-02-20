package com.reddy.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String fieldName, Object fieldValue) {
        super(String.format("Oops! " + fieldName + "->" + fieldValue + " already exists"));
    }
}
