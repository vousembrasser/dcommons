package com.dingwd.commons.reponse.exceptions.handler;

import com.dingwd.commons.constant.exceptions.DParamException;
import com.dingwd.commons.constant.exceptions.DServiceException;
import com.dingwd.commons.constant.messages.DErrorMessage;
import com.dingwd.commons.constraints.domain.GetErrorFieldInformation;
import com.dingwd.commons.reponse.ApiResponse;
import com.dingwd.commons.reponse.ErrorUtils;
import com.dingwd.commons.validation.param.ValidatorString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * DExceptionHandler
 * <p>
 * 异常的code以配置文件为准,优先级高于{@link DErrorMessage}中的code.
 */
@Slf4j
@ControllerAdvice
public class DExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ApiResponse handleException(Throwable e) {
        log.error("D_THROWABLE: ", e);
        DErrorMessage dErrorMessage = DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR;
        return new ApiResponse(dErrorMessage.getMessage(), dErrorMessage.getCode(), false);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleException(Exception e) {
        log.error("D_ERROR_EXCEPTION: ", e);
        DErrorMessage dErrorMessage = DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR;
        return new ApiResponse(dErrorMessage.getMessage(), dErrorMessage.getCode(), false);
    }

    @ExceptionHandler({DParamException.class})
    @ResponseBody
    public ApiResponse handlerDParamException(DParamException e) {
        log.error("D_ERROR_PARAM: ", e);
        return getApiResponse(e.getMessage(), e.getCode());
    }

    @ExceptionHandler({DServiceException.class})
    @ResponseBody
    public ApiResponse handlerDServiceException(DServiceException e) {
        log.error("D_ERROR_SERVICE: ", e);
        return getApiResponse(e.getMessage(), e.getCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ApiResponse handlerDServiceException(MethodArgumentNotValidException e) {
        log.error("D_ERROR_PARAM_VALIDATION: [{}]", e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Class[%s] filed[%s] codes[%s]".formatted(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue() + Arrays.toString(fieldError.getCodes())))
                .collect(Collectors.toList()));
        ObjectError error = e.getAllErrors().getFirst();


        for (Annotation annotation : error.unwrap(ConstraintViolationImpl.class).getRootBeanClass().getAnnotations()) {
            if (annotation instanceof GetErrorFieldInformation) {
                if (error instanceof FieldError fieldError) {
                    return getApiResponse(fieldError.getField() + ":" + fieldError.getRejectedValue() + " is error", null);
                }
            }
        }

        return getApiResponse(error.getDefaultMessage(), null);
    }

    private static ApiResponse getApiResponse(String message, String code) {
        String codeAndMessage = ErrorUtils.getErrorDesc(message);
        String[] getCodeAndMessage = codeAndMessage.split("\\|");

        if (getCodeAndMessage.length == 2) {
            return new ApiResponse(getCodeAndMessage[1], getCodeAndMessage[0], false);
        }

        if (ValidatorString.hasText(code)) {
            return new ApiResponse(message, code, false);
        }

        return new ApiResponse(message, DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR.getCode(), false);
    }
}