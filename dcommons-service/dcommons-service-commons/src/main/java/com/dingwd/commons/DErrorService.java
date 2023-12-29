package com.dingwd.commons;

public class DErrorService extends RuntimeException{

    public DErrorService() {
        super();
    }

    public DErrorService(String message) {
        super(message);
    }

    public DErrorService(String message, Throwable cause) {
        super(message, cause);
    }

    public DErrorService(Throwable cause) {
        super(cause);
    }

    protected DErrorService(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
