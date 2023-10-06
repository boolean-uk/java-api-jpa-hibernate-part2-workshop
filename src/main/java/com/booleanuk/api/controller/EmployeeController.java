package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.DepartmentRepository;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository repo;

    @Autowired
    DepartmentRepository departmentRepo;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return new ResponseEntity<>(
                repo.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getOne(@PathVariable(name="id") int id) {
        return new ResponseEntity<>(
                repo.findById(id).orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No employees with that id were found"
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Employee> createOne(@RequestBody Employee employee) {
        departmentRepo.findByNameAndLocation(
                employee.getDepartment().getName(),
                employee.getDepartment().getLocation()
        ).ifPresent(employee::setDepartment);

        return new ResponseEntity<>(
                repo.save(employee),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateOne(@PathVariable(name="id") int id, @RequestBody Employee employee) {
        Employee requested = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No employees with that id were found"
                )
        );

        requested.setFirstName(employee.getFirstName());
        requested.setLastName(employee.getLastName());

        departmentRepo.findByNameAndLocation(
                employee.getDepartment().getName(),
                employee.getDepartment().getLocation()
        ).ifPresentOrElse(
                requested::setDepartment,
                () -> requested.setDepartment(employee.getDepartment())
        );

        return new ResponseEntity<>(
                repo.save(requested),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteOne(@PathVariable(name="id") int id) {
        Employee deleted = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No employees with that id were found"
                                        )
                                );

        repo.deleteById(id);

        return new ResponseEntity<>(
                deleted,
                HttpStatus.OK
        );
    }
}
