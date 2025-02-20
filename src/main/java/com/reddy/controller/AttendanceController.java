package com.reddy.controller;

import com.reddy.exceptions.AttendanceAlreadyCheckedInException;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.attendence.Attendance;
import com.reddy.service.attendence.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<Attendance> checkIn(@RequestParam Long employeeId) {
        try {
            Attendance attendance = attendanceService.checkIn(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AttendanceAlreadyCheckedInException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<Attendance> checkOut(@RequestParam Long employeeId) {
        try {
            Attendance attendance = attendanceService.checkOut(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (ResourceNotFoundException | AttendanceAlreadyCheckedInException.AttendanceNotCheckedInException ex) {
            return ResponseEntity.notFound().build();
        } catch (AttendanceAlreadyCheckedInException.AttendanceAlreadyCheckedOutException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/today/{employeeId}")
    public ResponseEntity<Attendance> getTodaysAttendance(
            @PathVariable Long employeeId) {
        try {
            return ResponseEntity.ok(attendanceService.getTodaysAttendance(employeeId));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Attendance>> getAttendanceHistory(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(
                attendanceService.getAttendanceHistory(employeeId, startDate, endDate)
        );
    }
}