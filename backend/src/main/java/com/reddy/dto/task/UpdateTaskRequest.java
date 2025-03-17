package com.reddy.dto.task;

import com.reddy.enums.TaskPriority;
import com.reddy.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate endDate;
    private TaskStatus status;
    private Long assignedEmployeeId;
}
