package com.reddy.controller;

import com.reddy.dto.payroll.PayrollRequestDTO;
import com.reddy.dto.payroll.PayrollResponseDTO;
import com.reddy.enums.PaymentStatus;
import com.reddy.service.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollService payrollService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> generatePayroll(@Valid @RequestBody PayrollRequestDTO dto) {
        payrollService.generatePayroll(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public List<PayrollResponseDTO> getAllPayrolls() {
        return payrollService.getAllPayrolls();
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
