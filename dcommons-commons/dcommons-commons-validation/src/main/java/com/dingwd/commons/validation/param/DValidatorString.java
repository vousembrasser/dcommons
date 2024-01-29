package com.dingwd.commons.validation.param;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DValidatorString {

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
     * @param min   参数长度 最小长度 包含
     * @param max   参数长度 最大长度 包含
     * @return true or false
     */
    public static boolean lengthIn(String param, int min, int max) {
        return param.length() >= min && param.length() <= max;
    }

    /**
     * @param param 参数
     * @param min   参数长度 大于等于
     * @return true or false
     */
    public static boolean lengthMin(String param, int min) {
        return lengthMin(param, min, true);
    }


    /**
     * @param param 参数
     * @param min   参数 长度大于等于
     * @param equal 是否相等
     * @return true or false
     */
    public static boolean lengthMin(String param, int min, boolean equal) {
        if (equal) {
            return param.length() >= min;
        } else {
            return param.length() > min;
        }
    }

    /**
     * @param param 参数
     * @param max   小于等于 max
     * @return true or false
     */
    public static boolean lengthMax(String param, int max) {
        return lengthMax(param, max, true);
    }

    /**
     * @param param 参数
     * @param max   小于等于 max
     * @param equal 是否相等
     * @return true or false
     */
    public static boolean lengthMax(String param, int max, boolean equal) {
        if (equal) {
            return param.length() <= max;
        } else {
            return param.length() < max;
        }
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
