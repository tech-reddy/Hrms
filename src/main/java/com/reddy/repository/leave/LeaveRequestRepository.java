package com.reddy.repository.leave;

import com.reddy.enums.LeaveStatus;
import com.reddy.model.leave.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeEmpIdAndStatus(Long employeeId, LeaveStatus status);

    List<LeaveRequest> findByEmployeeEmpIdAndLeaveTypeId(Long employeeId, Long leaveTypeId);

    @Query("SELECT lr FROM LeaveRequest lr WHERE " +
            "lr.employee.department.id = :departmentId AND " +
            "lr.status = 'PENDING'")
    Page<LeaveRequest> findPendingByDepartment(Long departmentId, Pageable pageable);
     //List<LeaveRequest>findByEmployeeEmpIdAndStatus(Long id, LeaveStatus status);
     List<LeaveRequest> findByStatusAndEndDateBefore(LeaveStatus status, LocalDate date);

}
