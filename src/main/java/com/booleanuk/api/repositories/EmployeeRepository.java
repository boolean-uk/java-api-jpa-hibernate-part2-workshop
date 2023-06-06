package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}