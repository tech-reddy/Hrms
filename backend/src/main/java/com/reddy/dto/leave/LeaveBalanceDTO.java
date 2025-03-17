package com.reddy.dto.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceDTO {
    private Long id;
    // We'll include the leave type's name instead of the full object.
    private String leaveType;
    private Integer totalDays;
    private Integer usedDays;
    private Integer remainingDays;
}
