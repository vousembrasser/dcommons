package com.dingwd.rom.service;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SearchFilter {
    public enum Operator {
        EQ, LIKE_ALL, LIKE_RIGHT, GT, LT, GTE, LTE, IN, IS_NULL, IS_NOTNULL
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public static SearchFilter build(String fieldName, Operator operator, Object value) {
        return new SearchFilter(fieldName, operator, value);
    }

    public static SearchFilter build(String fieldName, Operator operator) {
        return new SearchFilter(fieldName, operator);
    }

    public SearchFilter(String fieldName, Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<>();

        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
  			/*if (StringUtils.isBlank((String) value)) {
  				continue;
  			}*/

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            // 创建searchFilter
            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}