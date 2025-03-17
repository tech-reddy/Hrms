package com.reddy.dto.designation;

import com.reddy.model.Designation;
import lombok.Data;

@Data
// Response
public class DesignationResponse {
    private Long id;
    private String title;
    private String grade;

    public DesignationResponse(Designation designation) {
        this.id = designation.getId();
        this.title = designation.getTitle();
        this.grade = designation.getGrade();
    }
}
