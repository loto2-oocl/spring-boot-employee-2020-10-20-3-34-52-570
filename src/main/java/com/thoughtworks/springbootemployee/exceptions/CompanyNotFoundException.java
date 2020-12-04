package com.thoughtworks.springbootemployee.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String companyId) {
        super(String.format("Company with id: %s not found", companyId));
    }
}
