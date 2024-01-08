package com.dingwd.commons.constant.exceptions;


import com.dingwd.commons.constant.messages.DErrorMessage;

public class DServiceException extends DRuntimeException {

    public DServiceException() {
        super();
    }

    public <T extends DErrorMessage> DServiceException(T message) {
        super(message.getMessage(), message.getCode());
    }

}
