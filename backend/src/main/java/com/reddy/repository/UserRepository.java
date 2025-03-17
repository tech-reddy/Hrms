package com.reddy.repository;

import com.reddy.enums.Role;
import com.reddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    //boolean findByIsPasswordResetRequired(boolean isPasswordResetRequired);
    boolean existsByRole(Role role);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier OR u.employee.email = :identifier")
    Optional<User> findByUserOrEmail(String identifier);
}
