package com.dingwd.commons.reponse;

import com.dingwd.commons.constant.messages.DErrorMessage;
import com.dingwd.commons.reponse.annotation.DGetMapping;
import com.dingwd.commons.reponse.annotation.DPostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

@Slf4j
@ControllerAdvice
public class DResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        //获取 Controller 类上的注解 是否有 DPostMapping 注解 并判断 DPostMapping 注解的 bodyWrap 是否为 true
        for (Annotation methodAnnotation : methodParameter.getMethodAnnotations()) {
            if (methodAnnotation instanceof DPostMapping dPostMapping) {
                return dPostMapping.bodyWrap() && dPostMapping.dataWrap();
            } else if (methodAnnotation instanceof DGetMapping dGetMapping) {
                return dGetMapping.bodyWrap() && dGetMapping.dataWrap();
            }
        }
        return false;

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 在这里对正常返回的响应进行封装
        if (!(body instanceof ResponseEntity)) {
            //Object body 转成 json字符串

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                log.error("json序列化失败: ", e);
                DErrorMessage dErrorMessage = DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR;
                return new ApiResponse(dErrorMessage.getMessage(), dErrorMessage.getCode(), false);
            }
            return new ApiResponse(jsonString, HttpStatus.OK.value() + "", true);
        }
        return body;
    }
}