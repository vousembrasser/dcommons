package com.dingwd.commons.constant.nums.ip;

public enum IPFormatTypeEnum {

    SINGLE(1),

    MARK(2),

    RANGE(3);

    private final int code;

    IPFormatTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
