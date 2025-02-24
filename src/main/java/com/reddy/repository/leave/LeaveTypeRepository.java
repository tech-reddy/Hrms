package com.reddy.repository.leave;

import com.reddy.model.leave.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

    Optional<LeaveType> findByName(String name);

    @Query("SELECT lt FROM LeaveType lt WHERE lt.carryForwardAllowed = true")
    List<LeaveType> findAllCarryForwardTypes();

    boolean existsByName(String name);
}
