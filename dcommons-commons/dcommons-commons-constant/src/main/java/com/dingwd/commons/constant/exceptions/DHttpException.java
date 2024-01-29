package com.dingwd.commons.constant.exceptions;


import com.dingwd.commons.constant.messages.DErrorMessage;

public class DHttpException extends DRuntimeException {

    public DHttpException() {
        super();
    }

    public <T extends DErrorMessage> DHttpException(T message) {
        super(message.getMessage(), message.getCode());
    }

}
