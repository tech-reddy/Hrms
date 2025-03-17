package com.reddy.dto.project;

import com.reddy.enums.ProjectStatus;
import lombok.Data;

@Data
public class ProjectSimpleResponse {
    private Long id;
    private String name;
    private ProjectStatus status;
}
