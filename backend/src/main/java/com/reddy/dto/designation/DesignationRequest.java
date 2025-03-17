package com.reddy.dto.designation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Request
@Data
public class DesignationRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Grade is required")
    private String grade;
}

