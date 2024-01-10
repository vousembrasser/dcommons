package com.dingwd.commons.constraints.domain.validators;

import com.dingwd.commons.constraints.domain.ValidatorParamLength;
import com.dingwd.commons.validation.param.ValidatorString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

public class ParamLengthValidator implements ConstraintValidator<ValidatorParamLength, Object> {

    @Override
    public boolean isValid(Object length, ConstraintValidatorContext constraintValidatorContext) {

        ValidatorParamLength validatorParamLength = (ValidatorParamLength) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor().getAnnotation();

        int maxTemp = validatorParamLength.max();
        int minTemp = validatorParamLength.min();
        Integer max = maxTemp != -1 ? maxTemp : null;
        Integer min = minTemp != -1 ? minTemp : null;
        if (length instanceof Iterable iterableDomain) {
            for (Object o : iterableDomain) {
                if (o instanceof String param) {
                    boolean check = getCheck(param, min, max);
                    if (!check) {
                        return false;
                    }
                } else if (o instanceof Number param) {
                    boolean check = getCheck(String.valueOf(param), min, max);
                    if (!check) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else if (length instanceof String param) {
            return getCheck(param, min, max);
        } else if (length instanceof Number param) {
            return getCheck(String.valueOf(param), min, max);
        }
        return false;
    }

    private static boolean getCheck(String param, Integer min, Integer max) {
        if (max != null && min != null) {
            return ValidatorString.lengthIn(param, min, max);
        } else if (max != null) {
            return ValidatorString.lengthMax(param, max);
        } else if (min != null) {
            return ValidatorString.lengthMin(param, min);
        } else {
            return false;
        }
    }
}