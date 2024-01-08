package com.dingwd.commons.constraints.domain;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, METHOD})
@Retention(RUNTIME)
@Documented
public @interface GetErrorFieldInformation {

    String[] fields() default {};

}
