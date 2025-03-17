package com.reddy.exceptions;

public class LeaveConflictException extends RuntimeException{
    public LeaveConflictException(String message) {
        super(message);
    }
}
