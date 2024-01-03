package com.dingwd.commons.messages;


@FunctionalInterface
public interface DErrorMessage {

    String getMessage();

    record MESSAGE(String message) implements DErrorMessage {
        public String getMessage() {
            return message;
        }
    }

    interface IP_ERROR {
        DErrorMessage IP_INVALID = new MESSAGE("ip.error.invalid");

    }


    interface PARAM_ERROR extends DErrorMessage {
        DErrorMessage IS_NULL = new MESSAGE("param.error.is.null");
        DErrorMessage PARAM_IS_INVALID = new MESSAGE("param.error.is.invalid");

        DErrorMessage NO_TEXT = new MESSAGE("param.error.no.text");

        DErrorMessage ENUM_NOT_EXIST = new MESSAGE("enum.not.exist");
    }

    interface SERVICE extends DErrorMessage {
        DErrorMessage SERVICE_ERROR = new MESSAGE("service.error");
    }

}
