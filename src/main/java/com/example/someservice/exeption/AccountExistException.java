package com.example.someservice.exeption;

public class AccountExistException extends RuntimeException{
    public AccountExistException(String message) {
        super(message);
    }
}
