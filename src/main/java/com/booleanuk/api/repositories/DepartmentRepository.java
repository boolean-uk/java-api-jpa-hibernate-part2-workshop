package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}