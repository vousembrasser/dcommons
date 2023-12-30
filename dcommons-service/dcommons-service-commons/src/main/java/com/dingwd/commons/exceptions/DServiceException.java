package com.dingwd.commons.exceptions;

import com.dingwd.commons.messages.DErrorMessage;

public class DServiceException extends RuntimeException{

    public DServiceException() {
        super();
    }

    public <T extends DErrorMessage> DServiceException(T message) {
        super(message.getMessage());
    }

    public DServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DServiceException(Throwable cause) {
        super(cause);
    }

    protected DServiceException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
