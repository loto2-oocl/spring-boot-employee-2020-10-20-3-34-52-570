package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.services.CompanyService;
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
        Company company = this.companyMapper.toEntity(companyRequest);
        Company createdCompany = this.companyService.create(company);

        return this.companyMapper.toResponse(createdCompany);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable String companyId, @RequestBody CompanyRequest companyRequest) {
        Company company = this.companyMapper.toEntity(companyRequest);
        Company updatedCompany = this.companyService.update(companyId, company);

        return this.companyMapper.toResponse(updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String companyId) {
        this.companyService.delete(companyId);
    }
}
