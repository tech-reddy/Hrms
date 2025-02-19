package com.reddy.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Long empId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String departmentName;
    private String roleName;
}
