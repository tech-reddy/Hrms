package com.reddy.service;

import com.reddy.dto.attendance.AttendanceDTO;
import com.reddy.exceptions.AttendanceAlreadyCheckedInException;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Employee;
import com.reddy.model.attendence.Attendance;
import com.reddy.repository.EmployeeRepository;
import com.reddy.repository.AttendanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // Check-in an employee
    public Attendance checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        LocalDate today = LocalDate.now();

        // Check if already checked in today
        Optional<Attendance> existingAttendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, today);

        if(existingAttendance.isPresent()) {
            throw new AttendanceAlreadyCheckedInException("Employee already checked in today");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(LocalTime.now());

        return attendanceRepository.save(attendance);
    }

    // Check-out an employee
    public Attendance checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findTopByEmployeeIdAndDateOrderByCheckInDesc(employeeId, today)
                .orElseThrow(() -> new AttendanceAlreadyCheckedInException.AttendanceNotCheckedInException("Employee hasn't checked in today"));

        if(attendance.getCheckOut() != null) {
            throw new AttendanceAlreadyCheckedInException.AttendanceAlreadyCheckedOutException("Employee already checked out");
        }

        attendance.setCheckOut(LocalTime.now());
        attendance.setTotalHours(calculateTotalHours(attendance.getCheckIn(), attendance.getCheckOut()));

        return attendanceRepository.save(attendance);
    }

    // Calculate hours between check-in and check-out
    private BigDecimal calculateTotalHours(LocalTime checkIn, LocalTime checkOut) {
        Duration duration = Duration.between(checkIn, checkOut);
        long minutes = duration.toMinutes();
        return BigDecimal.valueOf(minutes / 60.0).setScale(2, RoundingMode.HALF_UP);
    }

    // Get today's attendance for employee
    public AttendanceDTO getTodaysAttendance(Long employeeId) {
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("employeeId", employeeId + ""));
        return convertToDTO(attendance);
    }

    public List<AttendanceDTO> getAttendanceHistory(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        dto.setTotalHours(attendance.getTotalHours());

        if (attendance.getEmployee() != null) {
            String fullName = attendance.getEmployee().getFullName();
            dto.setEmployeeName(fullName);
        }

        return dto;
    }

    public long countPresentEmployees(LocalDate date) {
        return attendanceRepository.countPresentEmployees(date);
    }
}