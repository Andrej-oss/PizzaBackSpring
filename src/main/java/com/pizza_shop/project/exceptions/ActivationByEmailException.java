package com.pizza_shop.project.exceptions;

public class ActivationByEmailException extends RuntimeException {
    public ActivationByEmailException(String error){
        super(error);
    }
}
