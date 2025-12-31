package com.sphere.tongthuan.UserService.validator.impl;

import com.sphere.tongthuan.UserService.validator.DobConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobConstraintImpl implements ConstraintValidator<DobConstraint, LocalDate> {

	private int min;

	@Override
	public void initialize(DobConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

		if (Objects.isNull(value))
			return true;

		long yearsOld = ChronoUnit.YEARS.between(value, LocalDate.now());

		return yearsOld >= min;

	}
}
