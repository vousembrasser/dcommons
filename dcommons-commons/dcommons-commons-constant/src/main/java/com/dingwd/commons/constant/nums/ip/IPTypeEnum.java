package com.dingwd.commons.constant.nums.ip;


import com.dingwd.commons.constant.exceptions.DParamException;
import com.dingwd.commons.constant.messages.DErrorMessage;

public enum IPTypeEnum {


    IPv4(0),

    IPv6(1);

    IPTypeEnum(Integer code) {
        this.code = code;
    }


    private final int code;

    public int getCode() {
        return code;
    }

    public IPTypeEnum getEnum(Integer code) {

        if (code == null) {
            throw new DParamException(DErrorMessage.PARAM_ERROR.IS_NULL);
        }
        for (IPTypeEnum value : IPTypeEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new DParamException(DErrorMessage.PARAM_ERROR.ENUM_NOT_EXIST);
    }
}
