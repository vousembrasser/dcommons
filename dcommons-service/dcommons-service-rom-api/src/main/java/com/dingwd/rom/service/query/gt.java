package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

record gt<T extends Comparable<? super T>, Y extends T>(String attributeName, T value) implements Condition<Y> {
    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && value != null;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Y> root) {
        return builder.greaterThan(root.get(attributeName), value);
    }
}