package com.reddy.service;

import com.reddy.dto.payroll.PayrollDetailDTO;
import com.reddy.dto.payroll.PayrollRequestDTO;
import com.reddy.dto.payroll.PayrollResponseDTO;
import com.reddy.enums.ComponentType;
import com.reddy.enums.PaymentStatus;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Employee;
import com.reddy.model.payroll.Payroll;
import com.reddy.model.payroll.PayrollDetail;
import com.reddy.model.payroll.SalaryComponent;
import com.reddy.repository.EmployeeRepository;
import com.reddy.repository.payroll.PayrollRepository;
import com.reddy.repository.payroll.SalaryComponentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryComponentRepository componentRepository;
    private final ModelMapper modelMapper;

    public void generatePayroll(PayrollRequestDTO dto) {

        // Prevent duplicate payroll generation

        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setPayPeriodStart(dto.getPayPeriodStart());
            payroll.setPayPeriodEnd(dto.getPayPeriodEnd());
            // Calculate salary components
            List<SalaryComponent> components = componentRepository.findAll();
            List<PayrollDetail> details = new ArrayList<>();
            BigDecimal basicSalary = employee.getSalary();

            components.forEach(component -> {
                PayrollDetail detail = new PayrollDetail();
                detail.setComponent(component);
                System.out.println(basicSalary);
                detail.setPayroll(payroll);
                detail.setAmount(calculateComponentAmount(component, basicSalary));
                System.out.println(calculateComponentAmount(component, basicSalary));
                details.add(detail);
            });
            payroll.setDetails(details);
            calculateTotals(payroll);
            payrollRepository.save(payroll);
        }
    }

    private BigDecimal calculateComponentAmount(SalaryComponent component, BigDecimal basic) {
        return switch (component.getType()) {
            case EARNING, DEDUCTION -> {
                if(component.getPercentage() != null) {
                    yield basic.multiply(component.getPercentage().divide(BigDecimal.valueOf(100)));
                }
                yield component.getFixedAmount();
            }
        };
    }

    private void calculateTotals(Payroll payroll) {
        BigDecimal earnings = payroll.getDetails().stream()
                .filter(d -> d.getComponent().getType() == ComponentType.EARNING)
                .map(PayrollDetail::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(earnings);

        BigDecimal deductions = payroll.getDetails().stream()
                .filter(d -> d.getComponent().getType() == ComponentType.DEDUCTION)
                .map(PayrollDetail::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(deductions);

        payroll.setGrossEarnings(earnings);
        payroll.setTotalDeductions(deductions);
        payroll.setNetSalary(earnings.subtract(deductions));
    }

    public PayrollResponseDTO getPayrollById(Long id) {
        return payrollRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found"));
    }

    public List<PayrollResponseDTO> getEmployeePayrollHistory(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PayrollResponseDTO mapToDTO(Payroll payroll) {
        PayrollResponseDTO dto = modelMapper.map(payroll, PayrollResponseDTO.class);
        System.out.println(payroll.getDetails().size());
        dto.setFirstName(payroll.getEmployee().getFirstName() + " " + payroll.getEmployee().getLastName());
        dto.setDetails(payroll.getDetails().stream()
                .map(this::mapDetailToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private PayrollDetailDTO mapDetailToDTO(PayrollDetail detail) {
        PayrollDetailDTO dto = new PayrollDetailDTO();
        dto.setComponentName(detail.getComponent().getName());
        dto.setComponentType(detail.getComponent().getType());
        dto.setAmount(detail.getAmount());
        return dto;
    }

    public PayrollResponseDTO updatePaymentStatus(Long id, PaymentStatus status) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payroll doesn't exist"));
        payroll.setPaymentStatus(status);
        PayrollResponseDTO dto = modelMapper.map(payroll, PayrollResponseDTO.class);
        return dto;
    }

    public List<PayrollResponseDTO> getAllPayrolls() {
        return payrollRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}