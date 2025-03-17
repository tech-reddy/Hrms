package com.reddy.controller;

import com.reddy.dto.leave.LeaveBalanceDTO;
import com.reddy.dto.leave.LeaveRequestDTO;
import com.reddy.dto.leave.LeaveRequestResponseDTO;
import com.reddy.dto.leave.LeaveTypeDTO;
import com.reddy.enums.LeaveStatus;
import com.reddy.model.leave.LeaveRequest;
import com.reddy.model.leave.LeaveType;
import com.reddy.repository.leave.LeaveRequestRepository;
import com.reddy.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final LeaveRequestRepository leaveRequestRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveRequestDTO dto) {
        return leaveService.applyLeave(dto);
    }

    @PutMapping("/{id}/approve")
    public LeaveRequestResponseDTO approveLeave(
            @PathVariable Long id,
            @RequestParam Long approverId, @RequestParam LeaveStatus status) {
        return leaveService.approveLeave(id, approverId, status);
    }

    @GetMapping("/balances/{employeeId}")
    public List<LeaveBalanceDTO> getLeaveBalances(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveBalances(employeeId);
    }

    @GetMapping("/pending/department")
    public List<LeaveRequestResponseDTO> getPendingLeaves(
            @RequestParam Long departmentId,
            @RequestParam  LeaveStatus status) {
        return leaveService.getPendingLeaveRequests(departmentId, status);
    }
    @PostMapping("/types")
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveType createLeaveType(@Valid @RequestBody LeaveTypeDTO dto) {
        return leaveService.createLeaveType(dto);
    }

    @GetMapping("/types")
    public List<LeaveType> getAllLeaveTypes() {
        return leaveService.getAllLeaveTypes();
    }

@GetMapping("/pending")
public List<LeaveRequestResponseDTO> getPendingLeaves() {

    return leaveService.getPendingLeaveRequests();
}
    @GetMapping("/pending/{employeeId}")
    public List<LeaveRequestResponseDTO> getEmployeePendingLeaves(@PathVariable Long employeeId) {

        return leaveService.getEmployeePendingLeaves(employeeId);
    }

private List<Sort.Order> parseSortOrders(String sort) {
    return Arrays.stream(sort.split(","))
            .map(s -> new Sort.Order(
                    s.endsWith("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    s.replace(",desc", "").replace(",asc", "")
            ))
            .collect(Collectors.toList());
    }

    @GetMapping("/count/pending")
    public long countPendingLeaves() {
        return leaveService.countPendingLeaves();
    }
}
