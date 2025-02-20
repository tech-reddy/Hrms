package com.reddy.controller;

import com.reddy.dto.leave.LeaveRequestDTO;
import com.reddy.dto.leave.LeaveRequestResponseDTO;
import com.reddy.dto.leave.LeaveTypeDTO;
import com.reddy.model.leave.LeaveBalance;
import com.reddy.model.leave.LeaveRequest;
import com.reddy.model.leave.LeaveType;
import com.reddy.service.leave.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveRequestDTO dto) {
        return leaveService.applyLeave(dto);
    }

    @PutMapping("/{id}/approve")
    public LeaveRequestResponseDTO approveLeave(
            @PathVariable Long id,
            @RequestParam Long approverId) {
        return leaveService.approveLeave(id, approverId);
    }

    @GetMapping("/balances/{employeeId}")
    public List<LeaveBalance> getLeaveBalances(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveBalances(employeeId);
    }

    @GetMapping("/pending")
    public Page<LeaveRequest> getPendingLeaves(
            @RequestParam Long departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return leaveService.getPendingLeaveRequests(departmentId, PageRequest.of(page, size));
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
}

/*@GetMapping("/pending")
public Page<LeaveRequest> getPendingLeaves(
        @RequestParam Long departmentId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "appliedDate,desc") String sort) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(parseSortOrders(sort))
    );

    return leaveService.getPendingLeaveRequests(departmentId, pageable);
}

private List<Sort.Order> parseSortOrders(String sort) {
    return Arrays.stream(sort.split(","))
            .map(s -> new Sort.Order(
                    s.endsWith("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    s.replace(",desc", "").replace(",asc", "")
            ))
            .collect(Collectors.toList());
}
}*/
