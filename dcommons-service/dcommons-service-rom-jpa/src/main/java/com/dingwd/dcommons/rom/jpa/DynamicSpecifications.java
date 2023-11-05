package com.dingwd.dcommons.rom.jpa;

import com.dingwd.rom.service.SearchFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DynamicSpecifications {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filters!=null && !filters.isEmpty()) {

                    List<Predicate> predicates =new ArrayList<>();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String fieldName = filter.fieldName;
                        Path expression;
                        if(fieldName.contains(".")){
                            String[] names = StringUtils.split(fieldName, ".");
                            expression = root.get(names[0]);
                            for (int i = 1; i < names.length; i++) {
                                expression = expression.get(names[i]);
                            }
                        }else{
                            expression = root.get(fieldName);
                        }
                        // logic operator
                        switch (filter.operator) {
                            case EQ -> predicates.add(builder.equal(expression, filter.value));
                            case LIKE_ALL -> predicates.add(builder.like(expression, "%" + filter.value + "%"));
                            case GT -> predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                            case LT -> predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                            case GTE ->
                                    predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                            case LTE ->
                                    predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                            case IN -> predicates.add(expression.in(filter.value));
                            case IS_NULL -> predicates.add(expression.isNull());
                            case IS_NOTNULL -> predicates.add(expression.isNotNull());
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }
                return builder.conjunction();
            }
        };
    }
}