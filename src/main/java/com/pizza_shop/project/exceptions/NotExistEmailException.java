package com.pizza_shop.project.exceptions;

public class NotExistEmailException extends RuntimeException {
    public NotExistEmailException(String message){
        super(message);
    }
}
