package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.util.Collection;

record inArray<T,Y>(String attributeName, T... value) implements Condition<Y>{
    @SafeVarargs
    public inArray {
    }

    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && value != null && value.length > 0;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Y> root) {
        return builder.in(root.get(attributeName)).value(value);
    }
}

record inCollection<T,Y>(String attributeName, Collection<T> value) implements Condition<Y>{
    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && value != null && !value.isEmpty();
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Y> root) {
        return builder.in(root.get(attributeName)).value(value);
    }
}