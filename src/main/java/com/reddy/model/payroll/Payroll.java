package com.reddy.model.payroll;

import com.reddy.enums.PaymentStatus;
import com.reddy.model.employee.Employee;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Table(name = "payroll")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate payPeriodStart;

    @Column(nullable = false)
    private LocalDate payPeriodEnd;

    @Column(precision = 12, scale = 2)
    private BigDecimal grossEarnings = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalDeductions = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    private BigDecimal netSalary = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayrollDetail> details = new ArrayList<>();

}
