package com.reddy.repository;

import com.reddy.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    Designation findByTitle(String title);
    Optional<Designation> findById(Long id);
    Designation findByIdAndTitle(Long id, String title);
    boolean existsByTitle(String title);

}
