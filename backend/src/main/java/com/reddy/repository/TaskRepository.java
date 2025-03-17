package com.reddy.repository;

import com.reddy.model.Employee;
import com.reddy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedEmployeeId(Long employeeId);

    boolean existsByAssignedBy(Employee employee);
}
