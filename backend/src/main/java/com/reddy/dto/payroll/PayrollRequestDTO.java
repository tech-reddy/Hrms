package com.reddy.dto.payroll;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PayrollRequestDTO {
    @NotNull
    @FutureOrPresent
    private LocalDate payPeriodStart;

    @NotNull
    @Future
    private LocalDate payPeriodEnd;
}
