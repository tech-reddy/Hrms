package com.reddy.service;

import com.reddy.dto.leave.LeaveBalanceDTO;
import com.reddy.dto.leave.LeaveRequestDTO;
import com.reddy.dto.leave.LeaveRequestResponseDTO;
import com.reddy.dto.leave.LeaveTypeDTO;
import com.reddy.enums.LeaveStatus;
import com.reddy.exceptions.*;
import com.reddy.model.Employee;
import com.reddy.model.leave.LeaveBalance;
import com.reddy.model.leave.LeaveRequest;
import com.reddy.model.leave.LeaveType;
import com.reddy.repository.EmployeeRepository;
import com.reddy.repository.leave.LeaveBalanceRepository;
import com.reddy.repository.leave.LeaveRequestRepository;
import com.reddy.repository.leave.LeaveTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepo;
    private final LeaveBalanceRepository leaveBalanceRepo;
    private final LeaveTypeRepository leaveTypeRepo;
    private final EmployeeRepository employeeRepo;
    private final ModelMapper modelMapper;

    public LeaveRequest applyLeave(LeaveRequestDTO dto) {
        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveType leaveType = leaveTypeRepo.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        validateLeaveApplication(employee, leaveType, dto);

        LeaveRequest request = new LeaveRequest();
        request.setEmployee(employee);
        request.setLeaveType(leaveType);
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setReason(dto.getReason());

        return leaveRequestRepo.save(request);
    }

    private void validateLeaveApplication(Employee employee, LeaveType leaveType, LeaveRequestDTO dto) {
        // Check date validity
        if(dto.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidLeaveDateException("Cannot apply for past dates");
        }

        // Check overlapping requests
        List<LeaveRequest> overlapping = leaveRequestRepo
                .findByEmployeeId(employee.getId())
                .stream()
                .filter(lr -> datesOverlap(lr, dto))
                .toList();

        if(!overlapping.isEmpty()) {
            throw new LeaveConflictException("Existing approved leave overlaps with these dates");
        }

        // Check available balance
        LeaveBalance balance = leaveBalanceRepo.findByEmployeeIdAndLeaveTypeId(employee.getId(), leaveType.getId())
                .orElseThrow(() -> new LeaveBalanceNotFoundException("Leave balance not found"));

        int requestedDays = calculateWorkingDays(dto.getStartDate(), dto.getEndDate());

        if(requestedDays > balance.getRemainingDays()) {
            throw new InsufficientLeaveBalanceException(
                    "Insufficient balance. Requested: %d, Available: %d"
                            .formatted(requestedDays, balance.getRemainingDays())
            );
        }
    }

    @Transactional
    public LeaveRequestResponseDTO approveLeave(Long requestId, Long approverId, LeaveStatus status) {
        LeaveRequest request = leaveRequestRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        Employee approver = employeeRepo.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));

        if(!isValidApprover(approver, request.getEmployee())) {
            throw new UnauthorizedApprovalException("Approver not authorized");
        }

        request.setStatus(status);
        request.setApprovedBy(approver);

        updateLeaveBalance(request);
        //LeaveRequestResponseDTO dto = modelMapper.map(leaveRequestRepo.save(request), LeaveRequestResponseDTO.class);
        //dto.setEmployeeName(request.getEmployee().getFirstName() + " " + request.getEmployee().getLastName());
        return modelMapper.map(leaveRequestRepo.save(request), LeaveRequestResponseDTO.class);
    }

    private void updateLeaveBalance(LeaveRequest request) {
        LeaveBalance balance = leaveBalanceRepo
                .findByEmployeeIdAndLeaveTypeId(request.getEmployee().getId(), request.getLeaveType().getId())
                .orElseThrow(() -> new LeaveBalanceNotFoundException("Leave balance not found"));

        int daysTaken = calculateWorkingDays(request.getStartDate(), request.getEndDate());
        balance.setUsedDays(balance.getUsedDays() + daysTaken);
        leaveBalanceRepo.save(balance);
    }

    private boolean isValidApprover(Employee approver, Employee requester) {
        // Implement organization-specific approval logic
        return approver.getUser().getRole() != null && "MANAGER".equalsIgnoreCase(approver.getUser().getRole().name())
                && requester.getDepartment().equals(approver.getDepartment());
    }

    private int calculateWorkingDays(LocalDate start, LocalDate end) {
        return (int) start.datesUntil(end.plusDays(1))
                .filter(date -> !isWeekend(date))
                .count();
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public List<LeaveBalanceDTO> getEmployeeLeaveBalances(Long employeeId) {
         return leaveBalanceRepo.findByEmployeeId(employeeId)
                 .stream()
                 .map(this::convertToDTO)
                 .toList();
    }
    public LeaveType createLeaveType(LeaveTypeDTO dto) {
        if(leaveTypeRepo.existsByName(dto.getName())) {
            throw new DataIntegrityViolationException("Leave type already exists");
        }

        LeaveType type = new LeaveType();
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        type.setMaxDays(dto.getMaxDays());
        type.setCarryForwardAllowed(dto.getCarryForwardAllowed());
        LeaveType savedType = leaveTypeRepo.save(type);


        List<Employee> employees = employeeRepo.findAll();
        for (Employee employee : employees) {
            LeaveBalance balance = new LeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setTotalDays(type.getMaxDays());
            balance.setUsedDays(0);
            leaveBalanceRepo.save(balance);
        }

        return savedType;
    }

    // New method to get all leave types
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepo.findAll();
    }

    public List<LeaveRequestResponseDTO> getPendingLeaveRequests() {
        return leaveRequestRepo.findPendingByStatus()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    private boolean datesOverlap(LeaveRequest lr, LeaveRequestDTO dto) {
        LocalDate start1 = lr.getStartDate();
        LocalDate end1 = lr.getEndDate();
        LocalDate start2 = dto.getStartDate();
        LocalDate end2 = dto.getEndDate();

        // Two periods overlap if the start of one period is not after the end of the other.
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void autoRejectExpiredLeaves() {
        List<LeaveRequest> pendingLeaves = leaveRequestRepo.findByStatusAndEndDateBefore(
                LeaveStatus.PENDING, LocalDate.now());
        pendingLeaves.forEach(leave -> leave.setStatus(LeaveStatus.REJECTED));
        leaveRequestRepo.saveAll(pendingLeaves);
    }

    @Scheduled(cron = "0 0 0 1 1 *") // Runs every Jan 1st
    public void annualLeaveReset() {
        List<LeaveType> carryForwardTypes = leaveTypeRepo.findAllCarryForwardTypes();

        carryForwardTypes.forEach(type -> {
            List<LeaveBalance> balances = leaveBalanceRepo.findByLeaveTypeId(type.getId());

            balances.forEach(balance -> {
                int carriedDays = type.getCarryForwardAllowed() ?
                        Math.min(balance.getRemainingDays(), type.getMaxDays() / 2) : 0;

                balance.setTotalDays(type.getMaxDays() + carriedDays);
                balance.setUsedDays(0);
            });

            leaveBalanceRepo.saveAll(balances);
        });
    }

    public List<LeaveRequestResponseDTO> getEmployeePendingLeaves(Long employeeId) {

        return leaveRequestRepo.findByEmployeeId(employeeId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<LeaveRequestResponseDTO> getPendingLeaveRequests(Long departmentId, LeaveStatus status) {
        return leaveRequestRepo.findByEmployee_Department_IdAndStatus(departmentId, status)
                .stream()
                .map(this::convertToDTO).toList();
    }
    LeaveRequestResponseDTO convertToDTO(LeaveRequest leaveRequest) {
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setReason(leaveRequest.getReason());
        dto.setLeaveType(leaveRequest.getLeaveType().getName());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setFirstName(leaveRequest.getEmployee().getFullName());
        dto.setStatus(leaveRequest.getStatus());
        dto.setAppliedDate(leaveRequest.getAppliedDate());
        dto.setId(leaveRequest.getId());

        return dto;
    };

    private LeaveBalanceDTO convertToDTO(LeaveBalance leaveBalance) {
        // Assuming LeaveType has a getName() method.
        String leaveTypeName = (leaveBalance.getLeaveType() != null)
                ? leaveBalance.getLeaveType().getName()
                : null;
        return new LeaveBalanceDTO(
                leaveBalance.getId(),
                leaveTypeName,
                leaveBalance.getTotalDays(),
                leaveBalance.getUsedDays(),
                leaveBalance.getRemainingDays()
        );
    }

    public long countPendingLeaves() {
        return leaveRequestRepo.countByStatus(LeaveStatus.APPROVED);
    }
}
