package com.reddy.dto.employee;

import com.reddy.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// Create Employee
@Data
public class CreateEmployeeRequest {
    @NotBlank
    private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\-\\s]{8,15}$", message = "Invalid phone number")
    private String phone;

    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date must be in past or present")
    private LocalDate hireDate;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private BigDecimal salary;

    @NotBlank private String username;  // Admin chooses username
    @NotNull
    private Long departmentId;
    @NotNull private Long designationId;
    @NotNull private Role role;         // ADMIN/MANAGER/EMPLOYEE
}

