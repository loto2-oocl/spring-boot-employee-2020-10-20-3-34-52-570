package com.thoughtworks.springbootemployee.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String companyId;
    private String companyName;
    private Integer employeesNumber;

    public Company() {
    }

    public Company(String companyName, Integer employeesNumber) {
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }

    public Company(String companyId, String companyName, Integer employeesNumber) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
