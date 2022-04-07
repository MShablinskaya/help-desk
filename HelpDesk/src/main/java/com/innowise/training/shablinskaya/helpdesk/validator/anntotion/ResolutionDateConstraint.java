package com.innowise.training.shablinskaya.helpdesk.validator.anntotion;

import com.innowise.training.shablinskaya.helpdesk.validator.ResolutionDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ResolutionDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResolutionDateConstraint {
    String message() default "Invalid date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
