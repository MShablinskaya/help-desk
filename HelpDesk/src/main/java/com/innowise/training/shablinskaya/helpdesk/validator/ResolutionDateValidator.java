package com.innowise.training.shablinskaya.helpdesk.validator;

import com.innowise.training.shablinskaya.helpdesk.validator.anntotion.ResolutionDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResolutionDateValidator implements ConstraintValidator<ResolutionDateConstraint, String> {
    @Override
    public void initialize(ResolutionDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
}
