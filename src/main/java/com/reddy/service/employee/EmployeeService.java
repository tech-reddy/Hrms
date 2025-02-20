package com.reddy.service.employee;

import com.reddy.dto.employee.EmployeeRequestDTO;
import com.reddy.dto.employee.EmployeeResponseDTO;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.employee.Department;
import com.reddy.model.employee.Employee;
import com.reddy.model.employee.Role;
import com.reddy.repository.employee.DepartmentRepository;
import com.reddy.repository.employee.EmployeeRepository;
import com.reddy.repository.employee.RoleRepository;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        // Check for existing email
        if(employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("Email already registered");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Employee employee = modelMapper.map(dto, Employee.class);
        employee.setDepartment(department);
        employee.setRole(role);

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponseDTO(savedEmployee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Prevent email duplication
        if(!employee.getEmail().equals(dto.getEmail())) {
            if(employeeRepository.existsByEmail(dto.getEmail())) {
                throw new DataIntegrityViolationException("Email already registered");
            }
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        modelMapper.map(dto, employee);
        employee.setDepartment(department);
        employee.setRole(role);

        return convertToResponseDTO(employeeRepository.save(employee));
    }

    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

    public Page<EmployeeResponseDTO> searchEmployees(String query, Pageable pageable) {
        return employeeRepository.searchEmployees(query, pageable)
                .map(this::convertToResponseDTO);
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = modelMapper.map(employee, EmployeeResponseDTO.class);
        dto.setFullName(employee.getFirstName() + " " + employee.getLastName());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setRoleName(employee.getRole().getName());
        return dto;
    }
}
