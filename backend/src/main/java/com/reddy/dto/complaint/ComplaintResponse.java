package com.reddy.dto.complaint;

import com.reddy.enums.ComplaintStatus;
import com.reddy.model.Complaint;
import lombok.Data;

import java.time.LocalDateTime;

// Response
@Data
public class ComplaintResponse {
    private Long id;
    private String description;
    private ComplaintStatus status;
    private String response;
    private LocalDateTime createdAt;
    private String employeeName;

    public ComplaintResponse(Complaint complaint) {
        this.id = complaint.getId();
        this.description = complaint.getDescription();
        this.status = complaint.getStatus();
        this.response = complaint.getResponse();
        this.createdAt = complaint.getCreatedAt();
        this.employeeName = complaint.getEmployee().getFirstName() + " "
                + complaint.getEmployee().getLastName();
    }
}
