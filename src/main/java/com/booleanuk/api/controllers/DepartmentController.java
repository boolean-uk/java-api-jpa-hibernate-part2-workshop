package com.booleanuk.api.controllers;


import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepository;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository  employeeRepository;

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return ResponseEntity.ok(this.departmentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getOne(@PathVariable(name = "id") int id) {
        Department department = this.departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(this.departmentRepository.save(department), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable(name = "id") int id, @RequestBody Department department) {
        Department toUpdate = this.departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such department found"));
        toUpdate.setName(department.getName());
        toUpdate.setLocation(department.getLocation());
        return new ResponseEntity<>(this.departmentRepository.save(toUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable(name = "id") int id) {
        Department toDelete = this.departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such department found"));

        this.employeeRepository.deleteAll(toDelete.getEmployees());
        this.departmentRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);
    }

}
