package com.reddy.dto.complaint;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Request
@Data
public class CreateComplaintRequest {
    @NotBlank(message = "Description is required")
    private String description;
}

