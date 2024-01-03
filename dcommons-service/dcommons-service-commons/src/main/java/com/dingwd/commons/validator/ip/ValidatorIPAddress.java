package com.dingwd.commons.validator.ip;

import com.dingwd.commons.nums.ip.IPFormatTypeEnum;
import com.dingwd.commons.nums.ip.IPTypeEnum;

public class ValidatorIPAddress {

    /**
     * @param ip     1.1.1.1 或者1.1.1.1/32
     * @param ipType ip类型
     * @return true or false
     */
    public static boolean isIp(String ip, IPTypeEnum ipType) {
        boolean isIp = false;
        switch (ipType) {
            case IPv4 -> isIp = ValidatorIPv4Address.isValidIPOrWithMask(ip);
            case IPv6 -> isIp = ValidatorIPv6Address.isValidIPOrWithMask(ip);
        }
        return isIp;
    }

    public static boolean isIp(String ip, IPTypeEnum ipType, IPFormatTypeEnum formatType) {

        boolean isIp = false;
        switch (ipType) {
            case IPv4 -> {
                switch (formatType) {
                    case SINGLE -> isIp = ValidatorIPv4Address.isValidSingleIP(ip);
                    case MARK -> isIp = ValidatorIPv4Address.isValidIPWithMask(ip);
                    case RANGE -> isIp = ValidatorIPv4Address.isValidIPRange(ip);
                }
            }
            case IPv6 -> {
                switch (formatType) {
                    case SINGLE -> isIp = ValidatorIPv6Address.isValidSingleIP(ip);
                    case MARK -> isIp = ValidatorIPv6Address.isValidIPWithMask(ip);
                    case RANGE -> isIp = ValidatorIPv6Address.isValidIPRange(ip);
                }
            }
        }
        return isIp;
    }
}
