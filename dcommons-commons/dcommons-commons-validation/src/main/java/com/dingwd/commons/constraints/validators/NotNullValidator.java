package com.dingwd.commons.constraints.validators;

import com.dingwd.commons.constraints.ValidatorNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullValidator implements ConstraintValidator<ValidatorNotNull, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof Iterable iterableValue) {
            for (Object temp : iterableValue) {
                if (temp == null) {
                    return false;
                }
            }
            return true;
        } else {
            return value != null;
        }
    }
}
