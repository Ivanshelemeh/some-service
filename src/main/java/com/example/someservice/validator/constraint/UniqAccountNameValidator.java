package com.example.someservice.validator.constraint;

import com.example.someservice.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqAccountNameValidator implements ConstraintValidator<UniqName,String> {

    private final AccountServiceImpl accountService;

    @Override
    public void initialize(UniqName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name!=null &&  !accountService.exitsAccountByName(name);
    }
}
