package com.tcc.api.shared.infrastructure.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "A senha deve ter no mínimo 8 caracteres, incluindo uma letra maiúscula, uma minúscula e um número";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
