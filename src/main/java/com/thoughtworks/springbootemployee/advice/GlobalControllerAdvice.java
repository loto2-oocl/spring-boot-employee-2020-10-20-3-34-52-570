package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handIllegalArgumentException(IllegalArgumentException exception){
        return new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.name());
    }
}