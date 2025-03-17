package com.reddy.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

// Response with credentials
@AllArgsConstructor
@Data
public class EmployeeCredentialsResponse {
    private Long id;
    private String username;
    private String temporaryPassword;  // Only shown once during creation
    private String email;
    private String firstName;
    private String lastName;
}
