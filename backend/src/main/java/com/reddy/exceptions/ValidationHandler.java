package com.reddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public Map<String, String> validationHandlerException(AlreadyExistsException ex) {
        Map<String, String> error= new HashMap<>();
        error.put("Message", ex.getMessage());
        return error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> validationHandlerException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Message", ex.getMessage());
        return error;
    }
    @ExceptionHandler({
            AttendanceAlreadyCheckedInException.class,
            AttendanceAlreadyCheckedInException.AttendanceAlreadyCheckedOutException.class
    })
    public Map<String, String> handleConflictExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Message", ex.getMessage());
        return error;
    }

    @ExceptionHandler(AttendanceAlreadyCheckedInException.AttendanceNotCheckedInException.class)
    public Map<String, String> handleNotCheckedInException(AttendanceAlreadyCheckedInException.AttendanceNotCheckedInException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Message", ex.getMessage());
        return error;
    }

    @ExceptionHandler(LeaveBalanceNotFoundException.class)
    public Map<String, String> handleLeaveBalanceNotFoundException(LeaveBalanceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Message", ex.getMessage());
        return error;
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleConflict(ConflictException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
