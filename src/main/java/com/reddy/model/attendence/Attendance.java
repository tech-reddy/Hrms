package com.reddy.model.attendence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reddy.model.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private LocalTime checkIn;
    private LocalTime checkOut;
    private BigDecimal totalHours;

    @PrePersist
    protected void onCreate() {
        if(date == null) {
            date = LocalDate.now();
        }
    }
}
