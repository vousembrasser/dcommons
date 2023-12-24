package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface Condition<T> {
    boolean isValid();
    Predicate toPredicate(CriteriaBuilder builder, Root<T> root);
}
