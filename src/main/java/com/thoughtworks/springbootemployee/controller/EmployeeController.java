package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return this.employeeService.getAll().stream()
            .map(employeeMapper::toResponse)
            .collect(Collectors.toList());
    }

    @GetMapping(params = {
        "page",
        "pageSize"
    })
    public Page<EmployeeResponse> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return this.employeeService.getAllPaginated(page, pageSize)
            .map(employeeMapper::toResponse);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getAllByGender(@RequestParam("gender") String gender) {
        return this.employeeService.getAllByGender(gender).stream()
            .map(employeeMapper::toResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse findEmployee(@PathVariable String employeeId) {
        Employee employee = this.employeeService.getOneById(employeeId);

        return employeeMapper.toResponse(employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = this.employeeService.create(employeeMapper.toEntity(employeeRequest));

        return employeeMapper.toResponse(employee);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable String employeeId, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = this.employeeService.update(employeeId, employeeMapper.toEntity(employeeRequest));

        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String employeeId) {
        this.employeeService.delete(employeeId);
    }
}
