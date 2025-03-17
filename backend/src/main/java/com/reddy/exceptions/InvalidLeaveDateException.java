package com.reddy.exceptions;

public class InvalidLeaveDateException extends RuntimeException{
    public InvalidLeaveDateException(String message) {
        super(message);
    }
}
