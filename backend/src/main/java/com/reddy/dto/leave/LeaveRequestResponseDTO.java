package com.reddy.dto.leave;

import com.reddy.enums.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class LeaveRequestResponseDTO {
    private Long id;
    private String firstName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;
    private String reason;
    private LocalDate appliedDate;
}

