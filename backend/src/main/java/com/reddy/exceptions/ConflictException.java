package com.reddy.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String fieldName, Object fieldValue) {
        super(String.format("Oops! " + fieldName + "->" + fieldValue + " already exists"));
    }
}
