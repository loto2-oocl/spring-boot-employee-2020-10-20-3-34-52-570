package com.thoughtworks.springbootemployee.dto;

public class CompanyResponse {
    private String companyId;
    private String companyName;
    private Integer employeesNumber;

    public CompanyResponse() {
    }

    public CompanyResponse(String companyId, String companyName, Integer employeesNumber) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }
}
