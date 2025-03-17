package com.reddy.repository;

import com.reddy.enums.ComplaintStatus;
import com.reddy.model.Complaint;
import com.reddy.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByEmployee(Employee employee);
    long countByStatus(ComplaintStatus status);
}
