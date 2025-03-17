package com.reddy.repository.payroll;

import com.reddy.enums.ComponentType;
import com.reddy.model.payroll.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, Long> {
    List<SalaryComponent> findByType(ComponentType type);
}
