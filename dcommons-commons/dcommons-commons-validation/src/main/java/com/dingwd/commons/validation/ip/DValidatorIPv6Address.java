package com.dingwd.commons.validation.ip;

import com.dingwd.commons.constant.utils.ip.IPAddressUtil;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DValidatorIPv6Address {

    public static boolean isValidSingleIP(String ip) {
        return IPAddressUtil.isIPv6LiteralAddress(ip);
    }

    public static boolean isValidIPWithMask(String ipWithMask) {
        String[] parts = ipWithMask.split("/");
        if (parts.length != 2) {
            return false;
        }

        String ip = parts[0];
        String mask = parts[1];

        if (!isValidSingleIP(ip)) {
            return false;
        }

        int maskInt;
        try {
            maskInt = Integer.parseInt(mask);
        } catch (NumberFormatException e) {
            return false;
        }

        return maskInt >= 0 && maskInt <= 128;
    }

    public static boolean isValidIPRange(String ipRange) {
        String[] parts = ipRange.split("-");
        if (parts.length != 2) {
            return false;
        }

        String ipStart = parts[0];
        String ipEnd = parts[1];

        if (!isValidSingleIP(ipStart) || !isValidSingleIP(ipEnd)) {
            return false;
        }

        BigInteger start, end;
        try {
            start = new BigInteger(1, InetAddress.getByName(ipStart).getAddress());
            end = new BigInteger(1, InetAddress.getByName(ipEnd).getAddress());
        } catch (UnknownHostException e) {
            return false;
        }

        return start.compareTo(end) > 0;
    }

    static boolean isValidIPOrWithMask(String singleIPOrIpWithMask) {
        String[] parts = singleIPOrIpWithMask.split("/");
        boolean haveMark = parts.length == 2;

        if (!isValidSingleIP(singleIPOrIpWithMask)) {
            return false;
        }

        if (haveMark) {
            String mask = parts[1];
            int maskInt;
            try {
                maskInt = Integer.parseInt(mask);
            } catch (NumberFormatException e) {
                return false;
            }

            return maskInt >= 0 && maskInt <= 128;
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(isValidSingleIP("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));  // true
//        System.out.println(isValidSingleIP("2001:db8:85a3::8a2e:370:7334"));  // true
//        System.out.println(isValidSingleIP("invalid_ip"));  // false
//        System.out.println(isValidIPWithMask("2001:db8:85a3::8a2e:370:7334/64"));  // true
//        System.out.println(isValidIPWithMask("invalid_ip/64"));  // false
//        System.out.println(isValidIPRange("2001:db8:85a3::8a2e:370:7334-2001:db8:85a3::8a2e:370:7335"));  // true
//        System.out.println(isValidIPRange("invalid_ip-2001:db8:85a3::8a2e:370:7335"));  // false

        System.out.println(IPAddressUtil.isIPv6LiteralAddress("2001:db8:85a3::8a2e:370:7334-2001:db8:85a3::8a2e:370:7335"));
    }
}
