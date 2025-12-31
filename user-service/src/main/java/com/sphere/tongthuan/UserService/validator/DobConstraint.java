package com.sphere.tongthuan.UserService.validator;

import com.sphere.tongthuan.UserService.validator.impl.DobConstraintImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({  FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = { DobConstraintImpl.class })
public @interface DobConstraint {

	String message() default "Invalid Dob!";

	int min();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };


}
