package com.reddy.repository.payroll;

import com.reddy.model.Employee;
import com.reddy.model.payroll.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface PayrollRepository extends JpaRepository<Payroll, Long> {


    List<Payroll> findByEmployeeIdAndPayPeriodStartBetween(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

    boolean existsByEmployeeIdAndPayPeriodStartAndPayPeriodEnd(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

    @Query("SELECT DISTINCT p FROM Payroll p LEFT JOIN FETCH p.details WHERE p.employee.id = :employeeId")
    List<Payroll> findByEmployeeId(@Param("employeeId") Long employeeId);
}
