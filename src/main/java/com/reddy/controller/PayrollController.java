package com.reddy.controller;

import com.reddy.dto.payroll.PayrollRequestDTO;
import com.reddy.dto.payroll.PayrollResponseDTO;
import com.reddy.enums.PaymentStatus;
import com.reddy.service.payroll.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollService payrollService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayrollResponseDTO generatePayroll(@Valid @RequestBody PayrollRequestDTO dto) {
        return payrollService.generatePayroll(dto);
    }

    @GetMapping("/{id}")
    public PayrollResponseDTO getPayrollById(@PathVariable Long id) {
        return payrollService.getPayrollById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PayrollResponseDTO> getEmployeePayrollHistory(
            @PathVariable Long employeeId) {
        return payrollService.getEmployeePayrollHistory(employeeId);
    }

    @PutMapping("/{id}/status")
    public PayrollResponseDTO updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        return payrollService.updatePaymentStatus(id, status);
    }
}
