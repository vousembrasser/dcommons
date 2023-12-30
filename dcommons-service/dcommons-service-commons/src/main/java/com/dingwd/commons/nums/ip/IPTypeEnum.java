package com.dingwd.commons.nums.ip;

import com.dingwd.commons.DAssert;
import com.dingwd.commons.exceptions.DParamException;
import com.dingwd.commons.messages.DErrorMessage;

public enum IPTypeEnum {


    IPv4(0),

    IPv6(1);

    IPTypeEnum(Integer code) {
        DAssert.notNil(code);
        this.code = code;
    }


    private final int code;

    public int getCode() {
        return code;
    }

    public IPTypeEnum getEnum(Integer code) {
        DAssert.notNil(code);
        for (IPTypeEnum value : IPTypeEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new DParamException(DErrorMessage.PARAM_ERROR.ENUM_NOT_EXIST);
    }
}
