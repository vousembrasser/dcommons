package com.dingwd.commons.reponse;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ErrorUtils {

    private static final ResourceBundleMessageSource resourceBundle = new ResourceBundleMessageSource();
    private static final String ZH_LANGUAGE = "CHINESE";
    private static final String EN_LANGUAGE = "AMERICAN/ENGLISH";
    private static final String FILE_KEYWORKS = "error";
    private static final String JAR_RESOURCES = "classpath*:" + FILE_KEYWORKS + "/*error*.properties";
    private static final String RESOURCES = "classpath*:*error*.properties";


    /**
     * 静态代码块。
     * 用于加载错误码配置文件
     */
    static {
        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            List nameListCn = new ArrayList();

            Resource[] jarResources = patternResolver.getResources(JAR_RESOURCES);
            for (Resource resource : jarResources) {
                String fileName = resource.getFilename();
                fileName = fileName.substring(0, fileName.indexOf(FILE_KEYWORKS) + 5);
                nameListCn.add(FILE_KEYWORKS + "/" + fileName);
            }

            Resource[] resources = patternResolver.getResources(RESOURCES);
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                fileName = fileName.substring(0, fileName.indexOf(FILE_KEYWORKS) + 5);
                nameListCn.add(fileName);
            }
            resourceBundle.setBasenames((String[]) nameListCn.toArray(new String[0]));

            resourceBundle.setCacheSeconds(5);
        } catch (Throwable localThrowable) {
        }
    }

    /**
     * 获取错误码描述信息
     *
     * @param errCode 错误码
     * @return
     */
    public static String getErrorDesc(String errCode) {
        return getErrorDesc(errCode, "CHINESE");
    }

    /**
     * 获取错误码描述信息
     *
     * @param errCode  错误码
     * @param userLang 国际化语言
     * @return
     */
    public static String getErrorDesc(String errCode, String userLang) {
        String errDesc = null;
        try {
            if ((null == userLang) || (ZH_LANGUAGE.equals(userLang))) {
                errDesc = resourceBundle.getMessage(errCode, null, Locale.SIMPLIFIED_CHINESE);
            } else if (EN_LANGUAGE.equals(userLang)) {
                errDesc = resourceBundle.getMessage(errCode, null, Locale.US);
            }
        } catch (NoSuchMessageException localNoSuchMessageException) {
            return errCode;
        }
        return errDesc;
    }

//    /**
//     * 获取错误码描述信息
//     *
//     * @param errCode 错误码
//     * @param args    错误描述信息中参数
//     * @return
//     */
//    public static String getParseErrorDesc(String errCode, String[] args) {
//        return getParseErrorDesc(errCode, ZH_LANGUAGE, args);
//    }
//
//    /**
//     * 获取错误码描述信息
//     *
//     * @param errCode  错误码
//     * @param userLang 国际化语言
//     * @param args     错误描述信息中参数
//     * @return
//     */
//    public static String getParseErrorDesc(String errCode, String userLang, String[] args) {
//        String errDesc = "";
//        try {
//            if ((null == userLang) || (ZH_LANGUAGE.equals(userLang)))
//                errDesc = resourceBundle.getMessage(errCode, args, Locale.SIMPLIFIED_CHINESE);
//            else if (EN_LANGUAGE.equals(userLang))
//                errDesc = resourceBundle.getMessage(errCode, args, Locale.US);
//        } catch (NoSuchMessageException localNoSuchMessageException) {
//        }
//        return errDesc;
//    }

    public static void main(String[] args) {
        System.out.println(ErrorUtils.getErrorDesc("test.error"));
    }
}