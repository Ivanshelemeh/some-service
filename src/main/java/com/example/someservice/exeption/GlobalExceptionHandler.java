package com.example.someservice.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchNotFoundResourceException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>
                (new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = PetNotFoundException.class)
    public ResponseEntity<AppError> catchPetException(PetNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>
                (new AppError(HttpStatus.NO_CONTENT.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<AppError> catchAccountException(AccountNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<AppError> catchAccountExistExeption(AccountExistException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }

}
