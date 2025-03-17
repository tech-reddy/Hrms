package com.reddy.dto.project;

import lombok.Data;

// Helper DTOs
@Data
public class EmployeeSimpleResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
