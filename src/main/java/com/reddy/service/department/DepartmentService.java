package com.reddy.service.department;

import com.reddy.model.employee.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department createDepartment(Department department);
    Department updateDepartment(Department department);
    void deleteDepartment(Long id);
}
