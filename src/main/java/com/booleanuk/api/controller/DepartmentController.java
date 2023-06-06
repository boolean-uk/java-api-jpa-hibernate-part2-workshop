package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.repository.DepartmentRepository;
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

    @GetMapping
    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<Department>(this.departmentRepository.save(department), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = null;
        department = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found"));
        return ResponseEntity.ok(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        Department departmentToUpdate = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found"));

        departmentToUpdate.setName(department.getName());
        departmentToUpdate.setLocation(department.getLocation());
        departmentToUpdate.setEmployees(department.getEmployees());
        return new ResponseEntity<Department>(this.departmentRepository.save(departmentToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable int id) {
        Department departmentToDelete = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found"));
        this.departmentRepository.delete(departmentToDelete);
        return ResponseEntity.ok(departmentToDelete);
    }
}
