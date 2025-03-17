package com.reddy.controller;

import com.reddy.model.User;
import com.reddy.dto.complaint.ComplaintResponse;
import com.reddy.dto.complaint.CreateComplaintRequest;
import com.reddy.dto.complaint.UpdateComplaintRequest;
import com.reddy.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping
    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ComplaintResponse> createComplaint(
            @Valid @RequestBody CreateComplaintRequest request,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(
                complaintService.createComplaint(request, user),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/my-complaints")
    //@PreAuthorize("hasRole('EMPLOYEE')")
    public List<ComplaintResponse> getMyComplaints(@AuthenticationPrincipal User user) {
        return complaintService.getMyComplaints(user);
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<ComplaintResponse> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ComplaintResponse updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComplaintRequest request
    ) {
        return complaintService.updateComplaint(id, request);
    }
    @GetMapping("/count")
    public Long getComplaintCount() {
        return complaintService.getComplaintCount();
    }
}