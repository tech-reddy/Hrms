package com.reddy.service;

import com.reddy.dto.employee.CreateEmployeeRequest;
import com.reddy.dto.employee.EmployeeCredentialsResponse;
import com.reddy.dto.employee.EmployeeResponse;
import com.reddy.dto.employee.UpdateEmployeeRequest;
import com.reddy.exceptions.ConflictException;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Department;
import com.reddy.model.Designation;
import com.reddy.model.Employee;
import com.reddy.model.User;
import com.reddy.model.leave.LeaveBalance;
import com.reddy.model.leave.LeaveType;
import com.reddy.repository.*;
import com.reddy.repository.leave.LeaveBalanceRepository;
import com.reddy.repository.leave.LeaveRequestRepository;
import com.reddy.repository.leave.LeaveTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final UserRepository userRepo;
    private final DepartmentRepository departmentRepo;
    private final DesignationRepository designationRepo;
    private final PasswordEncoder passwordEncoder;
    private final LeaveRequestRepository leaveRequestRepo;
    private final TaskRepository taskRepo;
    private final EmailService emailService;
    private final LeaveBalanceRepository leaveBalanceRepo;
    private final LeaveTypeRepository leaveTypeRepo;
    // Constructor injection

    public EmployeeCredentialsResponse createEmployee(CreateEmployeeRequest request) {
        // Validate unique username
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username already exists");
        }

        // Generate temporary password
        String tempPassword = generateRandomPassword();

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        //user.setPasswordResetRequired(true);  // Force password change
        User savedUser = userRepo.save(user);

        // Create Employee
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(departmentRepo.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        employee.setDesignation(designationRepo.findById(request.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found")));
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employee.setPhone(request.getPhone());
        employee.setUser(savedUser);
        employeeRepo.save(employee);
        emailService.sendEmployeeCredentials(employee.getEmail(), user.getUsername(), tempPassword);
        initializeLeaveBalancesForEmployee(employee);

        return new EmployeeCredentialsResponse(
                employee.getId(),
                user.getUsername(),
                tempPassword,  // Return unhashed temp password
                employee.getEmail(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

    private void initializeLeaveBalancesForEmployee(Employee employee) {
        List<LeaveType> leaveTypes = leaveTypeRepo.findAll();
        for (LeaveType type : leaveTypes) {
            LeaveBalance balance = new LeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setTotalDays(type.getMaxDays());
            balance.setUsedDays(0);
            leaveBalanceRepo.save(balance);
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Page<EmployeeResponse> searchEmployees(String query, Pageable pageable) {
        return employeeRepo.searchEmployees(query, pageable)
                .map(this::convertToResponse);
    }


    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        List<EmployeeResponse> employeeList =  employeeRepo.findAll()
                .stream()
                .map(this::convertToResponse).toList();
        return new PageImpl<>(employeeList, pageable, employeeList.size());
    }

    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Update basic fields
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
            employee.getUser().setEmail(request.getEmail());
        }

        // Update department
        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        // Update designation
        if (request.getDesignationId() != null) {
            Designation designation = designationRepo.findById(request.getDesignationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));
            employee.setDesignation(designation);
        }

        // Update user role if provided
        if (request.getRole() != null) {
            User user = employee.getUser();
            if (user != null) {
                user.setRole(request.getRole());
                userRepo.save(user);
            }
        }

        Employee updatedEmployee = employeeRepo.save(employee);
        return convertToResponse(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Check for dependent records
        if (leaveRequestRepo.existsByEmployee(employee)) {
            throw new ConflictException("Cannot delete employee with existing leave requests");
        }

        if (taskRepo.existsByAssignedBy(employee)) {
            throw new ConflictException("Cannot delete employee with assigned tasks");
        }

        // Delete employee (User will be cascade-deleted if properly configured)
        employeeRepo.delete(employee);
    }

    // Helper method to convert entity to response DTO
    private EmployeeResponse convertToResponse(Employee employee) {
        return new EmployeeResponse(employee);
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return convertToResponse(employee);
    }

    public long countEmployees() {
        return employeeRepo.count();
    }
}
