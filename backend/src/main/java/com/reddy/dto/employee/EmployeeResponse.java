package com.reddy.dto.employee;

import com.reddy.dto.department.DepartmentResponse;
import com.reddy.dto.designation.DesignationResponse;
import com.reddy.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private DepartmentResponse department;
    private DesignationResponse designation;
    private String role;

    // Constructor using entity
    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.phone = employee.getPhone();
        this.hireDate = employee.getHireDate();
        this.department = employee.getDepartment() != null ?
                new DepartmentResponse(employee.getDepartment()) : null;
        this.designation = employee.getDesignation() != null ?
                new DesignationResponse(employee.getDesignation()) : null;
        this.role = employee.getUser() != null ?
                employee.getUser().getRole().name() : null;
    }
}
