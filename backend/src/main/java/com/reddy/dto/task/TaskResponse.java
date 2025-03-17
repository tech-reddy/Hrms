package com.reddy.dto.task;

import com.reddy.dto.project.EmployeeSimpleResponse;
import com.reddy.dto.project.ProjectSimpleResponse;
import com.reddy.enums.TaskPriority;
import com.reddy.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeSimpleResponse assignedEmployee;
    private ProjectSimpleResponse project;
}
