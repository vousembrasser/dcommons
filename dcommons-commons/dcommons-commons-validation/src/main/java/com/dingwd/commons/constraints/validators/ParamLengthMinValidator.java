package com.dingwd.commons.constraints.validators;

import com.dingwd.commons.constraints.ValidatorMinLength;
import com.dingwd.commons.validation.param.ValidatorString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

public class ParamLengthMinValidator implements ConstraintValidator<ValidatorMinLength, Object> {

    @Override
    public boolean isValid(Object length, ConstraintValidatorContext constraintValidatorContext) {

        ValidatorMinLength validatorMinLength = (ValidatorMinLength) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor().getAnnotation();

        int minTemp = validatorMinLength.value();
        Integer min = minTemp != -1 ? minTemp : null;
        if (length instanceof Iterable iterableDomain) {
            for (Object o : iterableDomain) {
                if (o instanceof String param) {
                    boolean check = getCheck(param, min);
                    if (!check) {
                        return false;
                    }
                } else if (o instanceof Number param) {
                    boolean check = getCheck(String.valueOf(param), min);
                    if (!check) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else if (length instanceof String param) {
            return getCheck(param, min);
        } else if (length instanceof Number param) {
            return getCheck(String.valueOf(param), min);
        }
        return false;
    }

    private static boolean getCheck(String param, Integer min) {
        if (min == null) {
            return false;
        }
        return ValidatorString.lengthMin(param, min);
    }
}