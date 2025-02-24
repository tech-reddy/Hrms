package com.reddy.service.employee;

import com.reddy.dto.employee.DepartmentRequestDTO;
import com.reddy.dto.employee.DepartmentResponseDTO;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.employee.Department;
import com.reddy.repository.employee.DepartmentRepository;
import com.reddy.repository.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepo;
    private final EmployeeRepository employeeRepo;
    private final ModelMapper modelMapper;

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        if(departmentRepo.existsByName(dto.getName())) {
            throw new DataIntegrityViolationException("Department name already exists");
        }

        Department department = modelMapper.map(dto, Department.class);
        return mapToDTO(departmentRepo.save(department));
    }

    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        if(!department.getName().equals(dto.getName()) &&
                departmentRepo.existsByName(dto.getName())) {
            throw new DataIntegrityViolationException("New department name already exists");
        }

        modelMapper.map(dto, department);
        return mapToDTO(departmentRepo.save(department));
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        if(employeeRepo.existsByDepartmentId(id)) {
            throw new DataIntegrityViolationException(
                    "Cannot delete department with assigned employees"
            );
        }

        departmentRepo.delete(department);
    }

    public Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable) {
        return departmentRepo.findAll(pageable)
                .map(this::mapToDTO);
    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    public Page<DepartmentResponseDTO> searchDepartments(String query, Pageable pageable) {
        return departmentRepo.searchDepartments(query, pageable)
                .map(this::mapToDTO);
    }

    private DepartmentResponseDTO mapToDTO(Department department) {
        DepartmentResponseDTO dto = modelMapper.map(department, DepartmentResponseDTO.class);
        dto.setEmployeeCount(department.getEmployees().size());
        return dto;
    }
}
