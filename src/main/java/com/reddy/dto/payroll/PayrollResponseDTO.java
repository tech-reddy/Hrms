package com.reddy.dto.payroll;

import com.reddy.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PayrollResponseDTO {
    private Long id;
    private String firstName;
    private LocalDate payPeriodStart;
    private LocalDate payPeriodEnd;
    private BigDecimal grossEarnings;
    private BigDecimal totalDeductions;
    private BigDecimal netSalary;
    private PaymentStatus paymentStatus;
    private List<PayrollDetailDTO> details;
}

