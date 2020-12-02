package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll() {
        return this.companyRepository.findAll();
    }

    public List<Company> getAllPaginated(Integer page, Integer pageSize) {
        return this.companyRepository.findAllPaginated(page, pageSize);
    }

    public Company getOne(Integer companyId) {
        return this.companyRepository.findById(companyId);
    }

    public List<Employee> getCompanyEmployees(Integer companyId) {
        return this.companyRepository.findById(companyId).getEmployees();
    }

    public Company create(Company newCompany) {
        return this.companyRepository.create(newCompany);
    }

    public Company update(Integer companyId, Company companyUpdate) {
        return this.companyRepository.update(companyId, companyUpdate);
    }

    public void delete(Integer companyId) {
        this.companyRepository.delete(companyId);
    }
}
