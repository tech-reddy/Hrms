package com.reddy.service;

import com.reddy.dto.department.DepartmentRequest;
import com.reddy.dto.department.DepartmentResponse;
import com.reddy.exceptions.ConflictException;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Department;
import com.reddy.model.Employee;
import com.reddy.repository.DepartmentRepository;
import com.reddy.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Page<DepartmentResponse> getAllDepartments(Pageable pageable) {
        List<DepartmentResponse> departmentList = departmentRepository.findAll()
                .stream()
                .map(DepartmentResponse::new)
                .toList();

        return new PageImpl<>(departmentList, pageable, departmentList.size());
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        // Validate unique code
        if (departmentRepository.existsByName(request.getName())) {
            throw new ConflictException("Department code already exists");
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setCode(request.getCode());

        // Set manager if provided
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            department.setManager(manager);
        }

        return new DepartmentResponse(departmentRepository.save(department));
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Update fields
        department.setName(request.getName());

        // Only update code if it's not already used
        if (!department.getCode().equals(request.getCode())) {
            if (departmentRepository.existsByCode(request.getCode())) {
                throw new ConflictException("New department code already exists");
            }
            department.setCode(request.getCode());
        }

        // Update manager
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            department.setManager(manager);
        } else {
            department.setManager(null);
        }

        return new DepartmentResponse(departmentRepository.save(department));
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Check if department has employees
        if (employeeRepository.existsByDepartment(department)) {
            throw new ConflictException("Cannot delete department with assigned employees");
        }

        departmentRepository.delete(department);
    }

    public Page<DepartmentResponse> searchDepartments(String query, Pageable pageable) {
        List<DepartmentResponse> departmentList = departmentRepository.searchDepartments(query, pageable)
                .map(DepartmentResponse::new).toList();
        return new PageImpl<>(departmentList, pageable, departmentList.size());
    }
}
