package com.reddy.exceptions;

public class InsufficientLeaveBalanceException extends RuntimeException{
    public InsufficientLeaveBalanceException(String message) {
        super(message);
    }
}
