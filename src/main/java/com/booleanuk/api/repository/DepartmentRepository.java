package com.booleanuk.api.repository;

import com.booleanuk.api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByNameAndLocation(String name, String location);
}
