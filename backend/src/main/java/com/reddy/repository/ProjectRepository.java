package com.reddy.repository;

import com.reddy.model.Employee;
import com.reddy.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByAssignedEmployeesId(Long employeeId);
}