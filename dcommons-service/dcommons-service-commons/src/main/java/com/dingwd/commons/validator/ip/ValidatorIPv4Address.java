package com.dingwd.commons.validator.ip;


import com.dingwd.commons.utils.ip.IPAddressUtil;

public class ValidatorIPv4Address {


    public static boolean isValidSingleIP(String ip) {
        return IPAddressUtil.isIPv4LiteralAddress(ip);
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

        return maskInt >= 0 && maskInt <= 32;
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

        String[] startParts = ipStart.split("\\.");
        String[] endParts = ipEnd.split("\\.");

        for (int i = 0; i < 4; i++) {
            int startPart = Integer.parseInt(startParts[i]);
            int endPart = Integer.parseInt(endParts[i]);

            if (startPart > endPart) {
                return false;
            } else if (startPart < endPart) {
                return true;
            }
        }
        return true;
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
            return maskInt >= 0 && maskInt <= 32;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isValidSingleIP("192.168.0.1"));  // true
        System.out.println(isValidSingleIP("invalid_ip"));  // false
        System.out.println(isValidIPWithMask("192.168.0.1/24"));  // true
        System.out.println(isValidIPWithMask("invalid_ip/24"));  // false
        System.out.println(isValidIPRange("192.168.0.1-192.168.0.255"));  // true
        System.out.println(isValidIPRange("invalid_ip-192.168.0.255"));  // false


    }
}

