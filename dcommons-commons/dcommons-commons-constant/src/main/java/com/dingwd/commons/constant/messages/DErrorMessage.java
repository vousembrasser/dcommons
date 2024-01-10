package com.dingwd.commons.constant.messages;


public interface DErrorMessage {

    String getMessage();

    String getCode();

    record MESSAGE(String message, String code) implements DErrorMessage {
        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }

        public MESSAGE(String message) {
            this(message, null);
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

        DErrorMessage ENTITY_NULL = new MESSAGE("param.error.entity.null");
    }

    interface SERVICE extends DErrorMessage {
        DErrorMessage INTERNAL_SERVER_ERROR = new MESSAGE("internal.service.error", "500");
        DErrorMessage SERVICE_ERROR = new MESSAGE("service.error");

    }
}
