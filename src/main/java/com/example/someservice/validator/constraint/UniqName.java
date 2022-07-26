package com.example.someservice.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqAccountNameValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqName {
    String message() default "Account with  this username already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
