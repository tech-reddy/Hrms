package com.reddy.dto.document;

import com.reddy.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

// Request
@Data
public class UploadDocumentRequest {
    @NotBlank
    private DocumentType docType;

    @NotBlank
    @URL
    private String fileUrl;
}

