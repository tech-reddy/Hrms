package com.reddy.dto.leave;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LeaveTypeDTO {
    @NotBlank
    private String name;

    @Size(max = 500)
    private String description;

    @Min(1)
    private Integer maxDays;

    private Boolean carryForwardAllowed = false;
}
