package com.dingwd.commons.constraints.domain.validators;

import com.dingwd.commons.constraints.domain.GetErrorFieldInformation;
import com.dingwd.commons.constraints.domain.ValidatorIsDomain;
import com.dingwd.commons.validation.domain.ValidatorDomain;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validate that the object is not {@code null}.
 *
 * @author Emmanuel Bernard
 */
public class IsDomainValidator implements ConstraintValidator<ValidatorIsDomain, Object> {

    @Override
    public boolean isValid(Object domain, ConstraintValidatorContext constraintValidatorContext) {
        if (domain instanceof Iterable iterableDomain) {
            boolean allTrue = true;
            for (Object o : iterableDomain) {
                if (o instanceof String) {
                    boolean check = ValidatorDomain.getInstance().isValid((String) o);
                    if (!check) {
                        allTrue = false;
                    }
                } else {
                    return false;
                }
            }
            return allTrue;
        } else if (domain instanceof String) {
            return ValidatorDomain.getInstance().isValid((String) domain);
        }
        return false;
    }
}
