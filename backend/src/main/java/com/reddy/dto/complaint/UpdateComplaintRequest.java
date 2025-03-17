package com.reddy.dto.complaint;

import com.reddy.enums.ComplaintStatus;
import lombok.Data;

@Data
public class UpdateComplaintRequest {
    private ComplaintStatus status;
    private String response;
}
