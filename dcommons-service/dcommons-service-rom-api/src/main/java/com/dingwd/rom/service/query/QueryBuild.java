package com.dingwd.rom.service.query;

import com.dingwd.rom.service.util.CFunction;
import com.dingwd.rom.service.util.ConvertUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.*;
import java.util.function.Function;

public class QueryBuild {

    private List<equal> equalList = null;
    private List<isNull> isNullList = null;
    private List<gt> gtList = null;
    private List<gte> gteList = null;
    private List<lt> ltList = null;
    private List<lte> lteList = null;
    private List<between> betweenList = null;
    private List<likeRight> likeRightList = null;
    private List<in> inArray = null;
    private List<inList> inList = null;


    public static QueryBuild build() {
        return new QueryBuild();
    }


    public <T> Predicate done(CriteriaBuilder builder, Root<T> root) {

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getPredicates(equalList, (tuples) -> builder.equal(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(isNullList, (tuples) -> builder.isNull(root.get(tuples.attributeName()))));
        predicates.addAll(getPredicates(gtList, (tuples) -> builder.greaterThan(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(gtList, (tuples) -> builder.greaterThan(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(gteList, (tuples) -> builder.greaterThanOrEqualTo(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(ltList, (tuples) -> builder.lessThan(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(lteList, (tuples) -> builder.lessThanOrEqualTo(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(betweenList, (tuples) -> builder.between(root.get(tuples.attributeName()), tuples.x(), tuples.y())));
        predicates.addAll(getPredicates(likeRightList, (tuples) -> builder.like(root.get(tuples.attributeName()), tuples.value())));
        predicates.addAll(getPredicates(inArray, (tuples) -> root.get(tuples.attributeName()).in(tuples.value())));
        predicates.addAll(getPredicates(inList, (tuples) -> root.get(tuples.attributeName()).in(tuples.value())));
        return builder.and(predicates.toArray(new Predicate[0]));
    }


    private <T> List<Predicate> getPredicates(List<T> list, Function<T, Predicate> mapper) {
        return Optional.ofNullable(list)
                .filter(predicateList -> !predicateList.isEmpty())
                .map(predicateList -> predicateList.stream()
                        .map(mapper)
                        .toList())
                .orElse(Collections.emptyList());
    }

    public <Field> QueryBuild isNull(CFunction<Field, ?> fieldFun) {
        return this.isNull(ConvertUtil.convertToClassFieldName(fieldFun));
    }

    public <T,Field> QueryBuild equal(CFunction<Field, ?> fieldFun, T value) {
        return this.equal(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>,Field> QueryBuild gt(CFunction<Field, ?> fieldFun, T value) {
        return this.gt(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>,Field> QueryBuild gte(CFunction<Field, ?> fieldFun, T value) {
        return this.gte(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>,Field> QueryBuild lt(CFunction<Field, ?> fieldFun, T value) {
        return this.lt(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>,Field> QueryBuild lte(CFunction<Field, ?> fieldFun, T value) {
        return this.lte(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public <T extends Comparable<? super T>,Field> QueryBuild between(CFunction<Field, ?> fieldFun, T x, T y) {
        return this.between(ConvertUtil.convertToClassFieldName(fieldFun), x, y);
    }

    public <Field> QueryBuild likeRight(CFunction<Field, ?> fieldFun, String value) {
        return this.likeRight(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public final <T,Field> QueryBuild in(CFunction<Field, ?> fieldFun, T... value) {
        return this.in(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }

    public final <T,Field> QueryBuild in(CFunction<Field, ?> fieldFun, Collection<T> value) {
        return this.in(ConvertUtil.convertToClassFieldName(fieldFun), value);
    }


    public <T> QueryBuild isNull(String attributeName) {
        if (isNullList == null) {
            isNullList = new ArrayList<>();
        }
        isNullList.add(new isNull(attributeName));
        return this;
    }

    public <T> QueryBuild equal(String attributeName, T value) {
        if (equalList == null) {
            equalList = new ArrayList<>();
        }
        equalList.add(new equal<>(attributeName, value));
        return this;
    }

    public <T extends Comparable<? super T>> QueryBuild gt(String attributeName, T value) {
        if (gtList == null) {
            gtList = new ArrayList<>();
        }
        gtList.add(new gt<>(attributeName, value));
        return this;
    }

    public <T extends Comparable<? super T>> QueryBuild gte(String attributeName, T value) {
        if (gteList == null) {
            gteList = new ArrayList<>();
        }
        gteList.add(new gte<>(attributeName, value));
        return this;
    }

    public <T extends Comparable<? super T>> QueryBuild lt(String attributeName, T value) {
        if (ltList == null) {
            ltList = new ArrayList<>();
        }
        ltList.add(new lt<>(attributeName, value));
        return this;
    }

    public <T extends Comparable<? super T>> QueryBuild lte(String attributeName, T value) {
        if (lteList == null) {
            lteList = new ArrayList<>();
        }
        lteList.add(new lte<>(attributeName, value));
        return this;
    }

    public <T extends Comparable<? super T>> QueryBuild between(String attributeName, T x, T y) {
        if (betweenList == null) {
            betweenList = new ArrayList<>();
        }
        betweenList.add(new between<>(attributeName, x, y));
        return this;
    }

    public QueryBuild likeRight(String attributeName, String value) {
        if (likeRightList == null) {
            likeRightList = new ArrayList<>();
        }
        likeRightList.add(new likeRight(attributeName, value));
        return this;
    }

    public final <T> QueryBuild in(String attributeName, T... value) {
        if (inArray == null) {
            inArray = new ArrayList<>();
        }
        inArray.add(new in<>(attributeName, value));
        return this;
    }

    public final <T> QueryBuild in(String attributeName, Collection<T> value) {
        if (inList == null) {
            inList = new ArrayList<>();
        }
        inList.add(new inList<>(attributeName, value));
        return this;
    }


}
