package com.reddy.dto.project;

import com.reddy.model.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateProjectRequest {
    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in present or future")
    private LocalDate startDate;

    @Future(message = "End date must be in future")
    private LocalDate endDate;

    @NotNull(message = "Manager ID is required")
    private Long managerId;

    @NotEmpty(message = "At least one employee must be assigned")
    private List<Long> assignedEmployeeIds;
}

