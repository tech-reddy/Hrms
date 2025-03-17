package com.reddy.dto.document;

import com.reddy.enums.VerificationStatus;
import lombok.Data;

@Data
public class UpdateDocumentRequest {
    private VerificationStatus status;
}
