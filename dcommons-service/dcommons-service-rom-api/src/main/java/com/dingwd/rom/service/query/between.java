package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

record between<T extends Comparable<? super T>,Y extends T>(String attributeName, T x, T y) implements Condition<Y> {
    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && x != null && y != null;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Y> root) {
        return builder.between(root.get(attributeName), x, y);
    }
}