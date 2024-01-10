package com.dingwd.commons.constraints.domain;

import com.dingwd.commons.constraints.domain.ValidatorParamLength.List;
import com.dingwd.commons.constraints.domain.validators.ParamLengthValidator;
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
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = ParamLengthValidator.class)
public @interface ValidatorParamLength {

    String message() default "d.validator.error.length";

    /**
     * max = -1 不检查
     */
    int max() default 10;

    /**
     * min = -1 不检查
     */
    int min() default 0;


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidatorParamLength[] value();
    }
}
