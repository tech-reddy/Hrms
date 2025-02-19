package com.reddy.repository.payroll;

import com.reddy.model.payroll.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeEmpIdAndPayPeriodStartBetween(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

    boolean existsByEmployeeEmpIdAndPayPeriodStartAndPayPeriodEnd(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

    List<Payroll> findByEmployeeEmpId(Long employeeId);
}
