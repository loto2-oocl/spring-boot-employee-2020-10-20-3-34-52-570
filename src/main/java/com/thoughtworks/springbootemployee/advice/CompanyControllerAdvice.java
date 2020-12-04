package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CompanyControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyNotFoundException(CompanyNotFoundException companyNotFoundException) {
        return new ErrorResponse(companyNotFoundException.getMessage(), HttpStatus.NOT_FOUND.name());
    }
}
