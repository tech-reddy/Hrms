package com.reddy.dto.employee;

import com.reddy.enums.Role;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private BigDecimal salary;
    private Long departmentId;
    private Long designationId;
    private Role role;

    // Getters & Setters
}
