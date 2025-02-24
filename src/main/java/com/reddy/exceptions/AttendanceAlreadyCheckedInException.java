package com.reddy.exceptions;

public class AttendanceAlreadyCheckedInException extends RuntimeException{
    public AttendanceAlreadyCheckedInException(String message) {
        super(message);
    }

    public static class AttendanceAlreadyCheckedOutException extends RuntimeException {
        public AttendanceAlreadyCheckedOutException(String message) {
            super(message);
        }
    }

    public static class AttendanceNotCheckedInException extends RuntimeException{
        public AttendanceNotCheckedInException(String message) {
            super(message);
        }
    }
}
