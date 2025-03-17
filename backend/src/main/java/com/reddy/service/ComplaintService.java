package com.reddy.service;

import com.reddy.dto.complaint.ComplaintResponse;
import com.reddy.dto.complaint.CreateComplaintRequest;
import com.reddy.dto.complaint.UpdateComplaintRequest;
import com.reddy.enums.ComplaintStatus;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Complaint;
import com.reddy.model.Employee;
import com.reddy.model.User;
import com.reddy.repository.ComplaintRepository;
import com.reddy.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final EmployeeRepository employeeRepository;

    public ComplaintService(ComplaintRepository complaintRepository,
                            EmployeeRepository employeeRepository) {
        this.complaintRepository = complaintRepository;
        this.employeeRepository = employeeRepository;
    }

    public ComplaintResponse createComplaint(CreateComplaintRequest request, User user) {
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Complaint complaint = new Complaint();
        complaint.setDescription(request.getDescription());
        complaint.setEmployee(employee);

        return new ComplaintResponse(complaintRepository.save(complaint));
    }

    public List<ComplaintResponse> getMyComplaints(User user) {
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return complaintRepository.findByEmployee(employee)
                .stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll()
                .stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    public ComplaintResponse updateComplaint(Long id, UpdateComplaintRequest request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        if (request.getStatus() != null) complaint.setStatus(request.getStatus());
        if (request.getResponse() != null) complaint.setResponse(request.getResponse());

        return new ComplaintResponse(complaintRepository.save(complaint));
    }

    public Long getComplaintCount() {
        return complaintRepository.countByStatus(ComplaintStatus.OPEN);
    }
}
