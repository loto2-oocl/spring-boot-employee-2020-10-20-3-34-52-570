package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        return this.employeeRepository.create(newEmployee);
    }

    public Employee getOneById(Integer employeeId) {
        return this.employeeRepository.findById(employeeId);
    }

    public List<Employee> getAllByGender(String gender) {
        return this.employeeRepository.findByGender(gender);
    }

    public List<Employee> getAllPaginated(int page, int pageSize) {
        return this.employeeRepository.findAllPaginated(page, pageSize);
    }

    public Employee update(Integer employeeId, Employee newEmployee) {
        return null;
    }
}
