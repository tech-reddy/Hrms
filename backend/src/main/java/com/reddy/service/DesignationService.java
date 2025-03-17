package com.reddy.service;

import com.reddy.dto.designation.DesignationRequest;
import com.reddy.dto.designation.DesignationResponse;
import com.reddy.exceptions.ConflictException;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Designation;
import com.reddy.repository.DesignationRepository;
import com.reddy.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {
    private final DesignationRepository designationRepository;
    private final EmployeeRepository employeeRepository;

    public DesignationService(DesignationRepository designationRepository,
                              EmployeeRepository employeeRepository) {
        this.designationRepository = designationRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<DesignationResponse> getAllDesignations() {
        return designationRepository.findAll()
                .stream()
                .map(DesignationResponse::new)
                .toList();
    }

    public DesignationResponse createDesignation(DesignationRequest request) {
        if (designationRepository.existsByTitle(request.getTitle())) {
            throw new ConflictException("Designation title already exists");
        }

        Designation designation = new Designation();
        designation.setTitle(request.getTitle());
        designation.setGrade(request.getGrade());

        return new DesignationResponse(designationRepository.save(designation));
    }

    public DesignationResponse updateDesignation(Long id, DesignationRequest request) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        if (!designation.getTitle().equals(request.getTitle()) &&
                designationRepository.existsByTitle(request.getTitle())) {
            throw new ConflictException("New designation title already exists");
        }

        designation.setTitle(request.getTitle());
        designation.setGrade(request.getGrade());

        return new DesignationResponse(designationRepository.save(designation));
    }

    public void deleteDesignation(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        if (employeeRepository.existsByDesignation(designation)) {
            throw new ConflictException("Cannot delete designation assigned to employees");
        }

        designationRepository.delete(designation);
    }

    public DesignationResponse getDesignationById(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));
        return new DesignationResponse(designation);
    }
}