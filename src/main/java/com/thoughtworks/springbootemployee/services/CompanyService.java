package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return this.companyRepository.findAll(pageable).toList();
    }

    public Company getOne(String companyId) {
        return this.companyRepository.findById(companyId)
            .orElseThrow(RuntimeException::new);
    }

    public List<Employee> getCompanyEmployees(String companyId) {
        return this.companyRepository.findById(companyId)
            .orElseThrow(RuntimeException::new)
            .getEmployees();
    }

    public Company create(Company newCompany) {
        return this.companyRepository.insert(newCompany);
    }

    public Company update(String companyId, Company companyUpdate) {
        if (!this.companyRepository.existsById(companyId)) {
            throw new RuntimeException();
        }

        companyUpdate.setCompanyId(companyId);
        return this.companyRepository.save(companyUpdate);
    }

    public void delete(String companyId) {
        this.companyRepository.deleteById(companyId);
    }
}
