package com.reddy.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String fieldName, String value) {
        super(String.format("Oops! " + fieldName + "-> " + value + " Not found"));
    }
}
