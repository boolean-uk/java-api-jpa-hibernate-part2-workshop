package com.booleanuk.api.controllers;

import com.booleanuk.api.models.DTOs.DepartmentDTO;
import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.DTOs.DepartmentEmployeesDTO;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ------------ ENDPOINTS ------------ //

    //region // POST //
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        try {
            return new ResponseEntity<Department>(this.departmentRepository.save(department), HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create department, please check all required fields are correct."
            );
        }
    }
    //endregion

    //region // GET //
    @GetMapping
    public ResponseEntity<List<DepartmentEmployeesDTO>> getAllDepartments() {
        List<Department> departments = this.departmentRepository.findAll();
        if(departments.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No departments matching that id were found."
        );
        return ResponseEntity.ok(departments.stream()
                .map(x -> modelMapper.map(x, DepartmentEmployeesDTO.class))
                .collect(Collectors.toList())
        );
        //return ResponseEntity.ok(departments);
    }

    @GetMapping("/NoEmployees")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentsWithoutEmployees() {
        List<Department> departments = this.departmentRepository.findAll();
        if(departments.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No departments matching that id were found."
        );
        return ResponseEntity.ok(departments.stream()
                .map(x -> modelMapper.map(x, DepartmentDTO.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = null;
        department = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No departments matching that id were found."
        ));
        return ResponseEntity.ok(department);
    }
    //endregion

    //region // PUT //
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        Department departmentToUpdate = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No departments matching that id were found."
        ));
        departmentToUpdate.setName(department.getName());
        departmentToUpdate.setLocation(department.getLocation());

        try {
            return new ResponseEntity<Department>(this.departmentRepository.save(departmentToUpdate), HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the department's details, please check all required fields are correct."
            );
        }
    }
    //endregion

    //region // DELETE //
    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable int id) {
        Department departmentToDelete = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No departments matching that id were found."
        ));
        this.departmentRepository.delete(departmentToDelete);
        return ResponseEntity.ok(departmentToDelete);
    }
    //endregion
}
