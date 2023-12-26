package com.dingwd.commons.exceptions;

public class DParamException extends RuntimeException{

private static final long serialVersionUID = -7034897190745766939L;

    public DParamException() {
        super();
    }

    public DParamException(String message) {
        super(message);
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
