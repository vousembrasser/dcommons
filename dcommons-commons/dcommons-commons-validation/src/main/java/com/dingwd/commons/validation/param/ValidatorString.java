package com.dingwd.commons.validation.param;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ValidatorString {

    public static boolean isNull(Object param) {
        return param == null;
    }

    public static boolean isNilOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional<?> optional) {
            return !optional.isPresent();
        }
        if (obj instanceof CharSequence charSequence) {
            return charSequence.isEmpty();
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection<?> collection) {
            return collection.isEmpty();
        }
        if (obj instanceof Map<?, ?> map) {
            return map.isEmpty();
        }
        // else
        return false;
    }

    public static boolean hasText(String param) {
        return param != null && !param.isBlank();
    }

    public static boolean hasLength(String param) {
        return (param != null && !param.isEmpty());
    }


    /**
     * @param param 参数
     * @param min   最小长度
     * @param max   最大长度 -1
     * @return true or false
     */
    public static boolean lengthIn(String param, int min, int max) {
        return param.length() >= min && param.length() <= max;
    }

    /**
     * @param param 参数
     * @param min   最小长度
     * @return true or false
     */
    public static boolean lengthMin(String param, int min) {
        return param.length() >= min;
    }

    /**
     * @param param 参数
     * @param max   最大长度
     * @return true or false
     */
    public static boolean lengthMax(String param, int max) {
        return param.length() >= max;
    }

    /**
     * @param param    参数
     * @param function 自定义比较方法
     * @return true or false
     */
    public static boolean isTure(String param, Function<String, Boolean> function) {
        return function.apply(param);
    }

}
