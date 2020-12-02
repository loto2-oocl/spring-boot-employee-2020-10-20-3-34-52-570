package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return this.companyList;
    }

    public List<Company> findAllPaginated(Integer page, Integer pageSize) {
        int pageToSkip = page - 1;
        int numberOfCompaniesToSkip = pageToSkip * pageSize;

        return this.findAll().stream()
                .skip(numberOfCompaniesToSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company findById(Integer companyId) {
        return this.findAll().stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .orElse(null);
    }

    public Company create(Company newCompany) {
        companyList.add(newCompany);
        return newCompany;
    }
}
