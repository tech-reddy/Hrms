package com.reddy.dto.project;

import com.reddy.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateProjectRequest {
    private String name;
    private String description;
    private LocalDate endDate;
    private ProjectStatus status;
    private List<Long> assignedEmployeeIds;
}
