package com.dingwd.dcommons.rom.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The JpaDynamicQueryBuilder class is used to dynamically build criteria queries using the Java Persistence API (JPA).
 * This class provides methods to add different conditions to the query such as null check, equality, comparison,
 * range, and string matching. The final built query can be retrieved using the 'build' method.
 *
 * @param <T> the type of the entity for which the query is built
 */
public class JpaDynamicQueryBuilder<T> {
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<T> criteriaQuery;
    private final Root<T> root;
    private final List<Predicate> predicates;

    public JpaDynamicQueryBuilder(CriteriaBuilder criteriaBuilder, Class<T> entityClass) {
        this.criteriaBuilder = criteriaBuilder;
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(entityClass);
        this.predicates = new ArrayList<>();
    }

    public JpaDynamicQueryBuilder<T> isNotNull(String attributeName) {
        predicates.add(criteriaBuilder.isNotNull(root.get(attributeName)));
        return this;
    }

    public JpaDynamicQueryBuilder<T> isNull(String attributeName) {
        predicates.add(criteriaBuilder.isNull(root.get(attributeName)));
        return this;
    }


    public <FieldKind extends Comparable<? super FieldKind>> JpaDynamicQueryBuilder<T> equal(String attributeName, FieldKind value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(root.get(attributeName), value));
        }
        return this;
    }

    public <FieldKind extends Comparable<? super FieldKind>> JpaDynamicQueryBuilder<T> gt(String attributeName, FieldKind value) {
        if (value != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get(attributeName), value));
        }
        return this;
    }

    public <FieldKind extends Comparable<? super FieldKind>> JpaDynamicQueryBuilder<T> gte(String attributeName, FieldKind value) {
        if (value != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attributeName), value));
        }
        return this;
    }

    public <FieldKind extends Comparable<? super FieldKind>> JpaDynamicQueryBuilder<T> lt(String attributeName, FieldKind value) {
        if (value != null) {
            predicates.add(criteriaBuilder.lessThan(root.get(attributeName), value));
        }
        return this;
    }

    public <FieldKind extends Comparable<? super FieldKind>> JpaDynamicQueryBuilder<T> lte(String attributeName, FieldKind value) {
        if (value != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attributeName), value));
        }
        return this;
    }

    public <Y extends Comparable<? super Y>> JpaDynamicQueryBuilder<T> between(String attributeName, Y x, Y y) {
        if (x != null && y != null) {
            predicates.add(criteriaBuilder.between(root.get(attributeName), x, y));
        }
        return this;
    }

    public JpaDynamicQueryBuilder<T> likeAll(String attributeName, String value) {
        if (StringUtils.hasText(value)) {
            predicates.add(criteriaBuilder.like(root.get(attributeName), "%" + value + "%"));
        }
        return this;
    }

    public JpaDynamicQueryBuilder<T> likeRight(String attributeName, String value) {
        if (StringUtils.hasText(value)) {
            predicates.add(criteriaBuilder.like(root.get(attributeName), value + "%"));
        }
        return this;
    }

    public <FieldKind extends Collection<?>> JpaDynamicQueryBuilder<T> in(String attributeName, FieldKind value) {
        if (!CollectionUtils.isEmpty(value)) {
            predicates.add(criteriaBuilder.in(root.get(attributeName)));
        }
        return this;
    }

    @SafeVarargs
    public final <FieldKind> JpaDynamicQueryBuilder<T> in(String attributeName, FieldKind... values) {
        if (values != null && values.length != 0) {
            predicates.add(criteriaBuilder.in(root.get(attributeName)).value(values));
        }
        return this;
    }

    public CriteriaQuery<T> build() {
        return this.build(false);
    }

    public CriteriaQuery<T> build(boolean distinct) {
        // Apply joins
        criteriaQuery.distinct(distinct);
        // Apply other conditions
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        return criteriaQuery;
    }
}