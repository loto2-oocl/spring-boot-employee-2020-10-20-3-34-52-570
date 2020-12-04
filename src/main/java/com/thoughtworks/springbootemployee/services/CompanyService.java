package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> getAll() {
        return this.companyRepository.findAll();
    }

    public Page<Company> getAllPaginated(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return this.companyRepository.findAll(pageable);
    }

    public Company getOne(String companyId) {
        return this.companyRepository.findById(companyId)
            .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public List<Employee> getCompanyEmployees(String companyId) {
        if (!this.companyRepository.existsById(companyId)) {
            throw new CompanyNotFoundException(companyId);
        }

        return this.employeeRepository.findAllByCompanyId(companyId);
    }

    public Company create(Company newCompany) {
        return this.companyRepository.insert(newCompany);
    }

    public Company update(String companyId, Company companyUpdate) {
        if (!this.companyRepository.existsById(companyId)) {
            throw new CompanyNotFoundException(companyId);
        }

        companyUpdate.setCompanyId(companyId);
        return this.companyRepository.save(companyUpdate);
    }

    public void delete(String companyId) {
        this.companyRepository.deleteById(companyId);
    }
}
