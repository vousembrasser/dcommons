package com.dingwd.commons.validator.ip;

import com.dingwd.commons.nums.ip.IPFormatTypeEnum;
import com.dingwd.commons.nums.ip.IPTypeEnum;

public class IPAddressValidator {

    /**
     * @param ip     1.1.1.1 或者1.1.1.1/32
     * @param ipType ip类型
     * @return true or false
     */
    public static boolean isIp(String ip, IPTypeEnum ipType) {
        boolean isIp = false;
        switch (ipType) {
            case IPv4 -> isIp = IPv4AddressValidator.isValidIPOrWithMask(ip);
            case IPv6 -> isIp = IPv6AddressValidator.isValidIPOrWithMask(ip);
        }
        return isIp;
    }

    public static boolean isIp(String ip, IPTypeEnum ipType, IPFormatTypeEnum formatType) {

        boolean isIp = false;
        switch (ipType) {
            case IPv4 -> {
                switch (formatType) {
                    case SINGLE -> isIp = IPv4AddressValidator.isValidSingleIP(ip);
                    case MARK -> isIp = IPv4AddressValidator.isValidIPWithMask(ip);
                    case RANGE -> isIp = IPv4AddressValidator.isValidIPRange(ip);
                }
            }
            case IPv6 -> {
                switch (formatType) {
                    case SINGLE -> isIp = IPv6AddressValidator.isValidSingleIP(ip);
                    case MARK -> isIp = IPv6AddressValidator.isValidIPWithMask(ip);
                    case RANGE -> isIp = IPv6AddressValidator.isValidIPRange(ip);
                }
            }
        }
        return isIp;
    }
}
