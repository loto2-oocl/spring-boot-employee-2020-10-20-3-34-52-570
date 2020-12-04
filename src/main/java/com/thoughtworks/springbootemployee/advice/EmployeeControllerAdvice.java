package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {
        return new ErrorResponse(employeeNotFoundException.getMessage(), HttpStatus.NOT_FOUND.name());
    }
}
