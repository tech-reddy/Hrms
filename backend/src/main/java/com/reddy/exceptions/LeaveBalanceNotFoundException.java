package com.reddy.exceptions;

public class LeaveBalanceNotFoundException extends RuntimeException{
    public LeaveBalanceNotFoundException(String message) {
        super(message);
    }
}
