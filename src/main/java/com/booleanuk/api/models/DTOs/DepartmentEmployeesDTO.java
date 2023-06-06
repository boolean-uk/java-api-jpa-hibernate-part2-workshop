package com.booleanuk.api.models.DTOs;

import java.util.ArrayList;
import java.util.List;

public class DepartmentEmployeesDTO {
    private int id;
    private String name;
    private String location;
    private List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
