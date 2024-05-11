package com.aht.UserManagementService.Validation.User;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameNotExistsValidator.class)
public @interface UsernameNotExists {
    String message() default "Username exists already!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
