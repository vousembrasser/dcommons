package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

record equal<T,Y>(String attributeName, T value) implements Condition<Y>{
    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && value != null;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Y> root) {
        return builder.equal(root.get(attributeName), value);
    }
}