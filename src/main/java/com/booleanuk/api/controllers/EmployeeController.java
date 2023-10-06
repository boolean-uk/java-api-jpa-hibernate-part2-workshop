package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Employee;
import com.booleanuk.api.models.DTOs.EmployeeDepartmentsDTO;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ------------ ENDPOINTS ------------ //

    //region // POST //
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            return new ResponseEntity<Employee>(this.employeeRepository.save(employee), HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create employee, please check all required fields are correct."
            );
        }
    }
    //endregion

    //region // GET //
    @GetMapping
    public ResponseEntity<List<EmployeeDepartmentsDTO>> getAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();
        if(employees.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No employees matching that id were found."
        );
        return ResponseEntity.ok(employees.stream()
                .map(x -> modelMapper.map(x, EmployeeDepartmentsDTO.class))
                .collect(Collectors.toList())
        );
        //return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = null;
        employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No employees matching that id were found."
        ));
        return ResponseEntity.ok(employee);
    }
    //endregion

    //region // PUT //
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee employeeToUpdate = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No employees matching that id were found."
        ));
        employeeToUpdate.setFirstName(employee.getFirstName());
        employeeToUpdate.setLastName(employee.getLastName());
        employeeToUpdate.setDepartment(employee.getDepartment());

        try {
            return new ResponseEntity<Employee>(this.employeeRepository.save(employeeToUpdate), HttpStatus.CREATED);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the employee's details, please check all required fields are correct."
            );
        }
    }
    //endregion

    //region // DELETE //
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) {
        Employee employeeToDelete = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No employees matching that id were found."
        ));
        this.employeeRepository.delete(employeeToDelete);
        return ResponseEntity.ok(employeeToDelete);
    }
    //endregion
}
