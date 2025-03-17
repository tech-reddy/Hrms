package com.reddy.dto.department;

import com.reddy.model.Department;
import lombok.Data;

@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private Long managerId;
    private String managerName;

    // Constructor from Entity
    public DepartmentResponse(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.code = department.getCode();
        if (department.getManager() != null) {
            this.managerId = department.getManager().getId();
            this.managerName = department.getManager().getFirstName() + " " + department.getManager().getLastName();
        }
    }
}