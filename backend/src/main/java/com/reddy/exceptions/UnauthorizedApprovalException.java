package com.reddy.exceptions;

public class UnauthorizedApprovalException extends RuntimeException{
    public UnauthorizedApprovalException(String message) {
        super(message);
    }
}
