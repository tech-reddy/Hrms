package com.reddy.dto.employee;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RoleResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Map<String, Boolean> permissions;
    private Integer employeeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
