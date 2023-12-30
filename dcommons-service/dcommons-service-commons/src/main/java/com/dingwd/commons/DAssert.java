package com.dingwd.commons;

import com.dingwd.commons.exceptions.DServiceException;
import com.dingwd.commons.exceptions.DParamException;
import com.dingwd.commons.messages.DErrorMessage;
import com.dingwd.commons.validator.mail.MailValidator;

public class DAssert {

    public static void notNil(Object param) {
        notNil(param, DErrorMessage.IP_ERROR.IP_INVALID);
    }

    public static <T extends DErrorMessage> void notNil(Object param, T message) {
        if (param == null) {
            throw new DParamException(message);
        }
    }

    public static void isNil(Object param) {
        if (param != null) {
            throw new DParamException(DErrorMessage.PARAM_ERROR.IS_NULL);
        }
    }

    public static void haveText(String param) {
        if (param == null || param.isBlank()) {
            throw new DParamException(DErrorMessage.PARAM_ERROR.NO_TEXT);
        }
    }


    public static void isEmail(String mail) {
        if (mail == null || mail.isBlank()) {
            throw new DParamException(DErrorMessage.PARAM_ERROR.IS_NULL);
        }
        if (!MailValidator.isMail(mail)) {
            throw new DParamException(DErrorMessage.PARAM_ERROR.PARAM_IS_INVALID);
        }
    }

    private static <T extends DErrorMessage> void isTrue(CheckFunction checkFunction, T message) {
        try {
            if (!checkFunction.execute()) {
                throw new DParamException(message);
            }
        } catch (Throwable throwable) {
            throw new DServiceException(throwable);
        }
    }
}
