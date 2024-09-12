package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Employee;
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

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = null;
        department = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with this ID not found"));
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<Department>(this.departmentRepository.save(department), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        Department departmentToUpDate = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with this Id can't be found"));

        departmentToUpDate.setId(department.getId());
        departmentToUpDate.setName(department.getLocation());
        departmentToUpDate.setLocation(department.getLocation());

        return new ResponseEntity<Department>(this.departmentRepository.save(departmentToUpDate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment (@PathVariable int id) {
        Department departmentDelete = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This department is not in the system!"));
        this.departmentRepository.delete(departmentDelete);
        return ResponseEntity.ok(departmentDelete);
    }
}
