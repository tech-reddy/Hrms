package com.reddy.dto.document;

import com.reddy.enums.DocumentType;
import com.reddy.enums.VerificationStatus;
import com.reddy.model.Document;
import lombok.Data;

import java.time.LocalDateTime;
@Data
// Response
public class DocumentResponse {
    private Long id;
    private DocumentType docType;
    private String fileUrl;
    private VerificationStatus status;
    private LocalDateTime uploadedDate;
    private LocalDateTime verifiedDate;
    private String employeeName;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.docType = document.getDocType();
        this.fileUrl = document.getFileUrl();
        this.status = document.getStatus();
        this.uploadedDate = document.getUploadedDate();
        this.verifiedDate = document.getVerifiedDate();
        this.employeeName = document.getEmployee().getFirstName() + " "
                + document.getEmployee().getLastName();
    }
}
