package com.dingwd.commons.messages;

public interface DErrorMessage {

    interface IP_ERROR extends DErrorMessage {
        String IP_INVALID = "ip.error.invalid";
    }

    interface PARAM_ERROR extends DErrorMessage {
        String IS_NULL = "param.error.is.null";
        String PARAM_IS_INVALID = "param.error.is.invalid";

        String NO_TEXT = "param.error.no.text";
    }

    interface SERVICE extends DErrorMessage {
        String SERVICE_ERROR = "service.error";
    }

}
