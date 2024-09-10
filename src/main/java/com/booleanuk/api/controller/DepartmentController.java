package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    DepartmentRepository repo;

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return new ResponseEntity<>(
                repo.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getOne(@PathVariable(name="id") int id) {
        Department requested = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No departments with that id were found"
                )
        );
        return new ResponseEntity<>(
                requested,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Department> createOne(@RequestBody Department department) {
        return new ResponseEntity<>(
                repo.save(department),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateOne(@PathVariable(name="id") int id, @RequestBody Department department) {
        Department requested = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No departments with that id were found"
                )
        );

        requested.setName(department.getName());
        requested.setLocation(department.getLocation());

        return new ResponseEntity<>(
                repo.save(requested),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Department> deleteOne(@PathVariable(name="id") int id) {
        Department deleted = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No departments with that id were found"
                                        )
                                );

        repo.deleteById(id);

        return new ResponseEntity<>(
                deleted,
                HttpStatus.OK
        );
    }
}
