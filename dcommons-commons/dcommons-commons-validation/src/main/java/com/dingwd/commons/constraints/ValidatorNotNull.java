package com.dingwd.commons.constraints;

import com.dingwd.commons.constraints.validators.NotNullValidator;
import com.dingwd.commons.constraints.validators.ParamLengthMinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(ValidatorNotNull.List.class)
@Documented
@Constraint(validatedBy = NotNullValidator.class)
public @interface ValidatorNotNull {

    String message() default "d.validator.error.params";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ValidatorNotNull[] value();
    }

}
