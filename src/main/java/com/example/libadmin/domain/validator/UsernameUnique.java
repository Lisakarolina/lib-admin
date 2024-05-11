package com.example.libadmin.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    String message() default "Username already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
