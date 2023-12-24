package com.dingwd.rom.service.query;

import com.dingwd.rom.service.util.CFunction;
import com.dingwd.rom.service.util.ConvertUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueryBuild {

    private List<Condition> conditions;

    private QueryBuild() {
    }

    private QueryBuild(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public static QueryBuild build() {
        return new QueryBuild(new ArrayList<>());
    }


    public <T> Predicate done(CriteriaBuilder builder, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(condition.toPredicate(builder, root)));
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    public <Field> QueryBuild isNull(CFunction<Field, ?> fieldFun) {
        return this.isNull(ConvertUtil.convertToClassFieldName(fieldFun));
    }

    public <T, Field> QueryBuild equal(CFunction<Field, ?> fieldFun, T value) {
        return this.equal(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>, Field> QueryBuild gt(CFunction<Field, ?> fieldFun, T value) {
        return this.gt(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>, Field> QueryBuild gte(CFunction<Field, ?> fieldFun, T value) {
        return this.gte(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>, Field> QueryBuild lt(CFunction<Field, ?> fieldFun, T value) {
        return this.lt(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>, Field> QueryBuild lte(CFunction<Field, ?> fieldFun, T value) {
        return this.lte(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>, Field> QueryBuild between(CFunction<Field, ?> fieldFun, T x, T y) {
        return this.between(ConvertUtil.convertToClassFieldName(fieldFun), x, y);
    }

    public <Field> QueryBuild likeRight(CFunction<Field, ?> fieldFun, String value) {
        return this.likeRight(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public final <T, Field> QueryBuild in(CFunction<Field, ?> fieldFun, T... value) {
        return this.in(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public final <T, Field> QueryBuild in(CFunction<Field, ?> fieldFun, Collection<T> value) {
        return this.in(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }


    public QueryBuild isNull(String attributeName) {
        return addCondition(new isNull<>(attributeName));

    }

    public <T> QueryBuild equal(String attributeName, T value) {
        return addCondition(new equal<>(attributeName, value));
    }

    public <T extends Comparable<? super T>> QueryBuild gt(String attributeName, T value) {
        return addCondition(new gt<>(attributeName, value));
    }

    public <T extends Comparable<? super T>> QueryBuild gte(String attributeName, T value) {
        return addCondition(new gte<>(attributeName, value));
    }

    public <T extends Comparable<? super T>> QueryBuild lt(String attributeName, T value) {
        return addCondition(new lt<>(attributeName, value));
    }

    public <T extends Comparable<? super T>> QueryBuild lte(String attributeName, T value) {
        return addCondition(new lte<>(attributeName, value));
    }

    public <T extends Comparable<? super T>> QueryBuild between(String attributeName, T x, T y) {
        return addCondition(new between<>(attributeName, x, y));
    }

    public QueryBuild likeRight(String attributeName, String value) {
        return addCondition(new likeRight(attributeName, value));
    }

    @SafeVarargs
    public final <T> QueryBuild in(String attributeName, T... value) {
        return addCondition(new inArray<>(attributeName, value));
    }

    public final <T> QueryBuild in(String attributeName, Collection<T> value) {
        return addCondition(new inCollection<>(attributeName, value));
    }

    public <T> QueryBuild addCondition(Condition<T> condition) {
        if (condition.isValid()) {
            conditions.add(condition);
        }
        return this;
    }


}
