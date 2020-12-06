package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        return this.companyService.getAll().stream()
            .map(companyMapper::toResponse)
            .collect(Collectors.toList());
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public Page<CompanyResponse> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return this.companyService.getAllPaginated(page, pageSize)
            .map(companyMapper::toResponse);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getOne(@PathVariable String companyId) {
        Company company = this.companyService.getOne(companyId);

        return this.companyMapper.toResponse(company);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getCompanyEmployees(@PathVariable String companyId) {
        return this.companyService.getCompanyEmployees(companyId).stream()
            .map(employeeMapper::toResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CompanyRequest companyRequest) {
        Company company = this.companyService.create(this.companyMapper.toEntity(companyRequest));

        return this.companyMapper.toResponse(company);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable String companyId, @RequestBody CompanyRequest companyRequest) {
        Company company = this.companyService.update(companyId, this.companyMapper.toEntity(companyRequest));

        return this.companyMapper.toResponse(company);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String companyId) {
        this.companyService.delete(companyId);
    }
}
