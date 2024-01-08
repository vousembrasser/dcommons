package com.dingwd.commons.constant.exceptions;


import com.dingwd.commons.constant.messages.DErrorMessage;

public class DParamException extends DRuntimeException {


    public DParamException() {
        super();
    }

    public <T extends DErrorMessage> DParamException(T message) {
        super(message.getMessage(), message.getCode());
    }
}
