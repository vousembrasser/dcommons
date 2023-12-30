package com.dingwd.commons.validator.param;

import java.util.function.Function;

public class StringValidator {


    /**
     * @param param 参数
     * @param min   最小长度
     * @param max   最大长度 -1
     * @return true or false
     */
    public static boolean length(String param, int min, int max) {
        return param.length() >= min && param.length() < max;
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
