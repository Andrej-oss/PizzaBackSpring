package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.ErrorResponse;
import com.pizza_shop.project.exceptions.EmailException;
import com.pizza_shop.project.exceptions.UserNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorController {


    @ExceptionHandler(value = UserNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUSerNameException(UserNameException e){
        log.warn("Handling UserNameException" + e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Choose another login");
    }

    @ExceptionHandler(value = EmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmailException(EmailException e){
        log.warn("Handling EmailException" + e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Insert another email");
    }

}
