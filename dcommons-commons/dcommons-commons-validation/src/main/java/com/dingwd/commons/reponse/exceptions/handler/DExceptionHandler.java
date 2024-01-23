package com.dingwd.commons.reponse.exceptions.handler;

import com.dingwd.commons.constant.exceptions.DParamException;
import com.dingwd.commons.constant.exceptions.DServiceException;
import com.dingwd.commons.constant.messages.DErrorMessage;
import com.dingwd.commons.constraints.GetErrorFieldInformation;
import com.dingwd.commons.reponse.ApiResponse;
import com.dingwd.commons.reponse.ErrorUtils;
import com.dingwd.commons.validation.collection.ValidatorCollection;
import com.dingwd.commons.validation.param.ValidatorString;
import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.StandardELContext;
import jakarta.el.ValueExpression;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.messageinterpolation.el.VariablesELContext;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        return getApiResponse(dErrorMessage.getMessage(), dErrorMessage.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleException(Exception e) {
        log.error("D_ERROR_EXCEPTION: ", e);
        DErrorMessage dErrorMessage = DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR;
        return getApiResponse(dErrorMessage.getMessage(), dErrorMessage.getCode());
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


        boolean show = false;
        if (error instanceof FieldError fieldError) {
            String message = error.getDefaultMessage();
            if (!"d.validator.error.params".equals(message)) {
                return getApiResponse(message, null);
            }

            Map map = fieldError.unwrap(ConstraintViolationImpl.class).getConstraintDescriptor().getAttributes();
            if (!ValidatorCollection.isEmpty(map)) {
                Object get = map.get("showErrorField");
                if (get instanceof Boolean temp) {
                    show = temp;
                } else if (get instanceof String temp) {
                    show = "true".equalsIgnoreCase(temp);
                }
            }
            boolean haveGetErrorFieldInformation = false;
            String[] informationFields = null;
            for (Annotation annotation : error.unwrap(ConstraintViolationImpl.class).getRootBeanClass().getAnnotations()) {
                if (annotation instanceof GetErrorFieldInformation information) {
                    haveGetErrorFieldInformation = true;
                    informationFields = information.fields();
                    break;
                }
            }

            if (!haveGetErrorFieldInformation) {
                return getApiResponse(message, null);
            } else {
                if (informationFields == null || informationFields.length == 0) {
                    Map<String, String> fieldAndValue = Map.of("field", fieldError.getField(),
                            "value", String.valueOf(fieldError.getRejectedValue()));
                    return getApiResponse(message + ".detail", fieldAndValue, null);
                }
                List<String> list = Arrays.stream(informationFields).toList();
                if (list.contains(fieldError.getField())) {
                    Map<String, String> fieldAndValue = Map.of("field", fieldError.getField(),
                            "value", String.valueOf(fieldError.getRejectedValue()));
                    return getApiResponse(message + ".detail", fieldAndValue, null);
                }
            }
        }
        return getApiResponse(error.getDefaultMessage(), null);
    }

    //LocaleContextMessageInterpolator
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

    private static ApiResponse getApiResponse(String message, Map<String, String> fieldAndValue, String code) {
        String codeAndMessage = ErrorUtils.getErrorDesc(message);
        ExpressionFactory factory = ExpressionFactory.newInstance();
        // 创建 ELContext
        ELContext context = new StandardELContext(new VariablesELContext(factory));
        context.getVariableMapper().setVariable("field", factory.createValueExpression(fieldAndValue.get("field"), String.class));
        context.getVariableMapper().setVariable("value", factory.createValueExpression(fieldAndValue.get("value"), String.class));
        ValueExpression valueExpression = factory.createValueExpression(context, codeAndMessage, String.class);
        codeAndMessage = valueExpression.getValue(context);

        String[] getCodeAndMessage = codeAndMessage.split("\\|", 1);

        if (getCodeAndMessage.length == 2) {
            return new ApiResponse(getCodeAndMessage[1], getCodeAndMessage[0], false);
        }

        if (ValidatorString.hasText(code)) {
            return new ApiResponse(message, code, false);
        }

        return new ApiResponse(message, DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR.getCode(), false);
    }
}