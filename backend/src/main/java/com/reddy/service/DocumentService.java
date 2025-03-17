package com.reddy.service;

import com.reddy.dto.document.DocumentResponse;
import com.reddy.dto.document.UpdateDocumentRequest;
import com.reddy.dto.document.UploadDocumentRequest;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Document;
import com.reddy.model.Employee;
import com.reddy.model.User;
import com.reddy.repository.DocumentRepository;
import com.reddy.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;

    public DocumentService(DocumentRepository documentRepository,
                           EmployeeRepository employeeRepository) {
        this.documentRepository = documentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DocumentResponse uploadDocument(UploadDocumentRequest request, User user) {
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Document document = new Document();
        document.setDocType(request.getDocType());
        document.setFileUrl(request.getFileUrl());
        document.setEmployee(employee);

        return new DocumentResponse(documentRepository.save(document));
    }

    public List<DocumentResponse> getMyDocuments(User user) {
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return documentRepository.findByEmployee(employee)
                .stream()
                .map(DocumentResponse::new)
                .toList();
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(DocumentResponse::new)
                .toList();
    }

    public DocumentResponse updateDocumentStatus(Long id, UpdateDocumentRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        document.setStatus(request.getStatus());
        document.setVerifiedDate(LocalDateTime.now());

        return new DocumentResponse(documentRepository.save(document));
    }
}
