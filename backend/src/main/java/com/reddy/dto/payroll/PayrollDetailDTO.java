package com.reddy.dto.payroll;

import com.reddy.enums.ComponentType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayrollDetailDTO {
    private String componentName;
    private ComponentType componentType;
    private BigDecimal amount;
}
