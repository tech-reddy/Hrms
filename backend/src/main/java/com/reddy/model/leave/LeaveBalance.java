package com.reddy.model.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddy.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    @JsonIgnore
    private LeaveType leaveType;

    @Column(nullable = false)
    private Integer totalDays;

    @Column(nullable = false)
    private Integer usedDays;

    @Column(nullable = false)
    private Integer remainingDays;

    @PrePersist
    @PreUpdate
    private void calculateRemaining() {
        this.remainingDays = this.totalDays - this.usedDays;
    }
}
