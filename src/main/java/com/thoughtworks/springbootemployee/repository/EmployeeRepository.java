package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Employee> findByGender(String gender) {
        return this.employeeList.stream()
                .filter(employee -> gender.equals(employee.getGender()))
                .collect(Collectors.toList());
    }

    public List<Employee> findAllPaginated(int page, int pageSize) {
        int pageToSkip = page - 1;
        int numberOfEmployeesToSkip = pageToSkip * pageSize;

        return this.findAll().stream()
                .skip(numberOfEmployeesToSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee update(Integer employeeId, Employee newEmployee) {
        this.findAll().stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> {
                    this.employeeList.remove(employee);
                    this.employeeList.add(newEmployee);
                });

        return newEmployee;
    }

    public void delete(Integer employeeId) {
        this.findAll().stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> this.employeeList.remove(employee));
    }
}
