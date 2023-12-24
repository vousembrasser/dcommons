package com.dingwd.rom.service.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

record likeRight(String attributeName, String value) implements Condition<String> {
    public likeRight {
        value = value + "%";
    }

    @Override
    public boolean isValid() {
        return StringUtils.hasLength(attributeName) && value != null;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<String> root) {
        return builder.like(root.get(attributeName), value);
    }
}