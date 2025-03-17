package com.reddy.repository;

import com.reddy.model.Document;
import com.reddy.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByEmployee(Employee employee);
}
