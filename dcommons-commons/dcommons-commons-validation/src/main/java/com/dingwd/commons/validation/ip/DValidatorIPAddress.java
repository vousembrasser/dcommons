package com.dingwd.commons.validation.ip;


import com.dingwd.commons.constant.nums.ip.IPFormatTypeEnum;
import com.dingwd.commons.constant.nums.ip.IPTypeEnum;

public class DValidatorIPAddress {

    /**
     * @param ip     1.1.1.1 或者1.1.1.1/32
     * @param ipType ip类型
     * @return true or false
     */
    public static boolean isIp(String ip, IPTypeEnum ipType) {
        boolean isIp = false;
        switch (ipType) {
            case IPTypeEnum.IPv4 -> isIp = DValidatorIPv4Address.isValidIPOrWithMask(ip);
            case IPTypeEnum.IPv6 -> isIp = DValidatorIPv6Address.isValidIPOrWithMask(ip);
        }
        return isIp;
    }

    public static boolean isIp(String ip, IPTypeEnum ipType, IPFormatTypeEnum formatType) {

        boolean isIp = false;
        switch (ipType) {
            case IPTypeEnum.IPv4 -> {
                switch (formatType) {
                    case IPFormatTypeEnum.SINGLE -> isIp = DValidatorIPv4Address.isValidSingleIP(ip);
                    case IPFormatTypeEnum.MARK -> isIp = DValidatorIPv4Address.isValidIPWithMask(ip);
                    case IPFormatTypeEnum.RANGE -> isIp = DValidatorIPv4Address.isValidIPRange(ip);
                }
            }
            case IPTypeEnum.IPv6 -> {
                switch (formatType) {
                    case IPFormatTypeEnum.SINGLE -> isIp = DValidatorIPv6Address.isValidSingleIP(ip);
                    case IPFormatTypeEnum.MARK -> isIp = DValidatorIPv6Address.isValidIPWithMask(ip);
                    case IPFormatTypeEnum.RANGE -> isIp = DValidatorIPv6Address.isValidIPRange(ip);
                }
            }
        }
        return isIp;
    }
}
