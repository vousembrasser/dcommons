package com.dingwd.commons.exceptions;

import com.dingwd.commons.messages.DErrorMessage;

public class DParamException extends RuntimeException {


    public DParamException() {
        super();
    }

    public <T extends DErrorMessage> DParamException(T message) {
        super(message.getMessage());
    }

    public DParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public DParamException(Throwable cause) {
        super(cause);
    }

    protected DParamException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
