package com.reddy.controller;

import com.reddy.dto.document.DocumentResponse;
import com.reddy.dto.document.UpdateDocumentRequest;
import com.reddy.dto.document.UploadDocumentRequest;
import com.reddy.model.User;
import com.reddy.service.DocumentService;
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
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @Valid @RequestBody UploadDocumentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(
                documentService.uploadDocument(request, user),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/my-documents")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<DocumentResponse> getMyDocuments(@AuthenticationPrincipal User user) {
        return documentService.getMyDocuments(user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<DocumentResponse> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public DocumentResponse updateDocumentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDocumentRequest request
    ) {
        return documentService.updateDocumentStatus(id, request);
    }
}
