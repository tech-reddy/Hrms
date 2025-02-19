package com.reddy.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class RoleRequestDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private Map<String, Boolean> permissions;
}

