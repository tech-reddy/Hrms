package com.reddy.dto.employee;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer employeeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
