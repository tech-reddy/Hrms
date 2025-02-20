package com.reddy.exceptions;

import com.reddy.model.leave.LeaveBalance;

public class LeaveBalanceNotFoundException extends RuntimeException{
    public LeaveBalanceNotFoundException(String message) {
        super(message);
    }
}
