package com.reddy.repository.attendence;

import com.reddy.model.attendence.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeEmpIdAndDate(Long employeeId, LocalDate data);

    Optional<Attendance> findTopByEmployeeEmpIdAndDateOrderByCheckInDesc(Long employeeId, LocalDate today);
    List<Attendance> findByEmployeeEmpIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}
