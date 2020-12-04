package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return this.employeeRepository.insert(newEmployee);
    }

    public Employee getOneById(String employeeId) {
        return this.employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id:%s not found", employeeId)));
    }

    public List<Employee> getAllByGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public Page<Employee> getAllPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return this.employeeRepository.findAll(pageable);
    }

    public Employee update(String employeeId, Employee newEmployee) {
        if (!this.employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException(String.format("Employee with id:%s not found", employeeId));
        }

        newEmployee.setId(employeeId);
        return this.employeeRepository.save(newEmployee);
    }

    public void delete(String employeeId) {
        this.employeeRepository.deleteById(employeeId);
    }
}
