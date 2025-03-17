package com.reddy.dto.task;

import com.reddy.enums.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be present or future")
    private LocalDate startDate;

    @Future(message = "End date must be in future")
    private LocalDate endDate;

    private Long projectId;

    @NotNull(message = "Assigned employee ID is required")
    private Long assignedEmployeeId;
}

