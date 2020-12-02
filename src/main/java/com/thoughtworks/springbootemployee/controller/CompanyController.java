package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAll() {
        return this.companyService.getAll();
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public List<Company> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return this.companyService.getAllPaginated(page, pageSize);
    }

    @GetMapping("/{companyId}")
    public Company getOne(@PathVariable Integer companyId) {
        return this.companyService.getOne(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable Integer companyId) {
        return this.companyService.getCompanyEmployees(companyId);
    }

    @PostMapping
    public Company create(@RequestBody Company newCompany) {
        return this.companyService.create(newCompany);
    }

    @PutMapping("/{companyId}")
    public Company update(@PathVariable Integer companyId, @RequestBody Company companyUpdate) {
        return this.companyService.update(companyId, companyUpdate);
    }
}
