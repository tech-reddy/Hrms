package com.reddy.repository;

import com.reddy.model.attendence.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate data);
    @Query("SELECT COUNT(DISTINCT a.employee.id) FROM Attendance a WHERE a.date = :today")
    long countPresentEmployees(@Param("today") LocalDate today);

    Optional<Attendance> findTopByEmployeeIdAndDateOrderByCheckInDesc(Long employeeId, LocalDate today);
    List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}
