package com.reddy.model.payroll;

import com.reddy.enums.ComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ComponentType type;

    private String description;

    @Column(precision = 5, scale = 2)
    private BigDecimal percentage;  // For percentage-based calculations

    @Column(precision = 12, scale = 2)
    private BigDecimal fixedAmount; // For fixed amount components
}
