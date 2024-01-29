package com.dingwd.commons.constant.exceptions;

import com.dingwd.commons.constant.messages.DErrorMessage;

public class DJsonException extends DRuntimeException {


    public DJsonException() {
        super();
    }

    public <T extends DErrorMessage> DJsonException(T message) {
        super(message.getMessage(), message.getCode());
    }
}
