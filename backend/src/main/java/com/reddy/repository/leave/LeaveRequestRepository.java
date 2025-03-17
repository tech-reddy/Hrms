package com.reddy.repository.leave;

import com.reddy.dto.leave.LeaveRequestResponseDTO;
import com.reddy.enums.LeaveStatus;
import com.reddy.model.Employee;
import com.reddy.model.leave.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);

    List<LeaveRequest> findByEmployeeIdAndLeaveTypeId(Long employeeId, Long leaveTypeId);
    long countByStatus(LeaveStatus status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE " +
            "lr.status = 'PENDING'")
    List<LeaveRequest> findPendingByStatus();
     List<LeaveRequest> findByEmployeeId(Long id);
     List<LeaveRequest> findByStatusAndEndDateBefore(LeaveStatus status, LocalDate date);

    boolean existsByEmployee(Employee employee);

    List<LeaveRequest> findByEmployee_Department_IdAndStatus(Long departmentId, LeaveStatus status);

    //List<LeaveRequest> findPendingByEmployeeId(Long id);
}
