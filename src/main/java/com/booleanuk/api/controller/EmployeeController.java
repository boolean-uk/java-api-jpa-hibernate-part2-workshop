package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.DepartmentRepository;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/newEmployees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return new ResponseEntity<>(this.employeeRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable int id){
        Employee employee = this.employeeRepository.
                findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    public record EmployeeRequest (String firstName, String lastName,int dep_id){}
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeRequest employee){

        Department dep = this.departmentRepository.
                findById(employee.dep_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found"));

        Employee toCreate = new Employee(employee.firstName,employee.lastName);toCreate.setDepartment(dep);
        toCreate.setDepartment(dep);

        return new ResponseEntity<>(this.employeeRepository.save(toCreate), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> create(@PathVariable int id, @RequestBody EmployeeRequest employee){

        employeeRepository.
                findById(employee.dep_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));

        Department dep = this.departmentRepository.
                findById(employee.dep_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found"));

        Employee toUpdate = new Employee(employee.firstName,employee.lastName);
        toUpdate.setDepartment(dep);

        return new ResponseEntity<>(this.employeeRepository.save(toUpdate), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> create(@PathVariable int id){

        Employee toDelete= employeeRepository.
                findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));
        this.employeeRepository.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

}
