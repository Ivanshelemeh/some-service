package com.example.someservice.exeption;

public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException(String message) {
        super(message);
    }
}
