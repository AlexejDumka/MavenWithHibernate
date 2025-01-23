package com.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Step 1: Create the custom annotation
@Constraint(validatedBy = {NotEmptyValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
    String message() default "Field must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}