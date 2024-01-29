package com.dingwd.commons.constraints.validators;

import com.dingwd.commons.constraints.ValidatorMaxLength;
import com.dingwd.commons.validation.param.DValidatorString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

public class ParamLengthMaxValidator implements ConstraintValidator<ValidatorMaxLength, Object> {

    @Override
    public boolean isValid(Object length, ConstraintValidatorContext constraintValidatorContext) {

        ValidatorMaxLength validatorMinLength = (ValidatorMaxLength) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor().getAnnotation();

        int maxTemp = validatorMinLength.value();
        Integer max = maxTemp != -1 ? maxTemp : null;
        if (length instanceof Iterable iterableDomain) {
            for (Object o : iterableDomain) {
                if (o instanceof String param) {
                    boolean check = getCheck(param, max);
                    if (!check) {

                        return false;
                    }
                } else if (o instanceof Number param) {
                    boolean check = getCheck(String.valueOf(param), max);
                    if (!check) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else if (length instanceof String param) {
            return getCheck(param, max);
        } else if (length instanceof Number param) {
            return getCheck(String.valueOf(param), max);
        }
        return false;
    }

    private static boolean getCheck(String param, Integer max) {
        if (max == null) {
            return false;
        }
        return DValidatorString.lengthMax(param, max);
    }
}