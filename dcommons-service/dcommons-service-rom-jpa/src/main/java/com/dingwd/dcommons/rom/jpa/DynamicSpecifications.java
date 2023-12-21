package com.dingwd.dcommons.rom.jpa;

import com.dingwd.rom.service.SearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

public class DynamicSpecifications<T> {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters) {
        return (root, query, builder) -> {
            if (filters == null || filters.isEmpty()) {
                return builder.conjunction();
            }

            List<Predicate> predicates = filters.stream()
                    .map(filter -> toPredicate(filter, root, builder))
                    .toList();

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> Predicate toPredicate(SearchFilter filter, Root<T> root, CriteriaBuilder builder) {
        Path expression = getPath(filter.fieldName, root);
        return switch (filter.operator) {
            case EQ -> builder.equal(expression, filter.value);
            case LIKE_ALL -> builder.like(expression, "%" + filter.value + "%");
            case LIKE_RIGHT -> builder.like(expression, filter.value + "%");
            case GT -> builder.greaterThan(expression, (Comparable) filter.value);
            case LT -> builder.lessThan(expression, (Comparable) filter.value);
            case GTE -> builder.greaterThanOrEqualTo(expression, (Comparable) filter.value);
            case LTE -> builder.lessThanOrEqualTo(expression, (Comparable) filter.value);
            case IN -> expression.in(filter.value);
            case IS_NULL -> expression.isNull();
            case IS_NOTNULL -> expression.isNotNull();
        };
    }

    private static <T> Path<String> getPath(String fieldName, Root<T> root) {
        if (!StringUtils.hasText(fieldName)) {
            throw new IllegalArgumentException("fieldName is empty");
        }

        if (!fieldName.contains(".")) {
            return root.get(fieldName);
        }

        String[] names = fieldName.split("\\.");
        Path<String> expression = root.get(names[0]);
        for (int i = 1; i < names.length; i++) {
            expression = expression.get(names[i]);
        }
        return expression;
    }
}