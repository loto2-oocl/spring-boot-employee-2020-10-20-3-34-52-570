package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return this.employeeService.getAll();
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public List<Employee> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return this.employeeService.getAllPaginated(page, pageSize).getContent();
    }

    @GetMapping(params = "gender")
    public List<Employee> getAllByGender(@RequestParam("gender") String gender) {
        return this.employeeService.getAllByGender(gender);
    }

    @GetMapping("/{employeeId}")
    public Employee findEmployee(@PathVariable String  employeeId) {
        return this.employeeService.getOneById(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return this.employeeService.create(employee);
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable String employeeId, @RequestBody Employee employeeUpdate) {
        return this.employeeService.update(employeeId, employeeUpdate);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String employeeId) {
        this.employeeService.delete(employeeId);
    }
}
