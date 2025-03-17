package com.reddy.dto.project;

import com.reddy.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private EmployeeSimpleResponse manager;
    private List<EmployeeSimpleResponse> assignedEmployees;
}

