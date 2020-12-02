package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList = new ArrayList<>();

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee create(Employee newEmployee) {
        employeeList.add(newEmployee);
        return newEmployee;
    }

    public Employee findById(Integer employeeId) {
        return this.employeeList.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .orElse(null);
    }
}
