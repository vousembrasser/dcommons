package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

record isNull<T>(String attributeName) implements Condition<T> {

    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName);
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<T> root) {
        return builder.isNull(root.get(attributeName));
    }
}