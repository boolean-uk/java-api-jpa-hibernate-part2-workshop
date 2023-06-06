package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/newEmployees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repo;
    @GetMapping
    public List<Employee> getAll(){
        return this.repo.findAll();
    }
}
