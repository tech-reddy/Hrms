package com.reddy.repository.employee;

import com.reddy.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
    boolean existsByDepartmentId(Long departmentId);
    boolean existsByRoleId(Long roleId);

    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    Page<Employee> findByDepartmentDeptId(@Param("departmentId") Long departmentId, Pageable pageable);

    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Employee> searchEmployees(@Param("query") String query, Pageable pageable);
}