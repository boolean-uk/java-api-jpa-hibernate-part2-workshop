package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.DepartmentRepository;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(this.employeeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable(name = "id") int id) {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Department department = this.departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such department."));
        employee.setDepartment(department);
        return new ResponseEntity<>(this.employeeRepository.save(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        Department department = this.departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such department."));

        Employee toUpdate = this.employeeRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO such employee."));
        toUpdate.setDepartment(department);
        toUpdate.setFirstName(employee.getFirstName());
        toUpdate.setLastName(employee.getLastName());
        return new ResponseEntity<>(this.employeeRepository.save(toUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(name = "id") int id) {
        Employee toDelete = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such employee"));

        this.employeeRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }

}


