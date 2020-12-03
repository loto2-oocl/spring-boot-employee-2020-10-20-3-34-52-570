package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        return this.employeeRepository.insert(newEmployee);
    }

    public Employee getOneById(String employeeId) {
        return this.employeeRepository
            .findById(employeeId)
            .orElseThrow(RuntimeException::new);
    }

    public List<Employee> getAllByGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getAllPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Employee> employeePage = this.employeeRepository.findAll(pageable);

        return employeePage.toList();
    }

    public Employee update(String employeeId, Employee newEmployee) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new RuntimeException();
        }

        Employee employee = optionalEmployee.get();
        employee.setName(newEmployee.getName());
        employee.setAge(newEmployee.getAge());
        employee.setGender(newEmployee.getGender());
        employee.setSalary(newEmployee.getSalary());

        return this.employeeRepository.save(employee);
    }

    public void delete(String employeeId) {
        this.employeeRepository.deleteById(employeeId);
    }
}
