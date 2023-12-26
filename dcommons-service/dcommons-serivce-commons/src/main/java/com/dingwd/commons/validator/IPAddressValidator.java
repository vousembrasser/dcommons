package com.dingwd.commons.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressValidator {

   public enum IPType {
        SINGLE_IP,
        CIDR,
        IP_RANGE
    }

    private static final String IP_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final String CIDR_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/(\\d{1,2})$";

    private static final String IP_RANGE_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)-" +
                    "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static boolean validIp(String ip, IPType ipType) {
        return switch (ipType) {
            case SINGLE_IP -> isValidIP(ip);
            case CIDR -> isValidCIDR(ip);
            case IP_RANGE -> isValidIPRange(ip);
        };
    }

    public static boolean isValidIP(String ip) {
        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean isValidCIDR(String ipWithMask) {
        Pattern pattern = Pattern.compile(CIDR_REGEX);
        Matcher matcher = pattern.matcher(ipWithMask);
        return matcher.matches();
    }

    public static boolean isValidIPRange(String ipRange) {
        Pattern pattern = Pattern.compile(IP_RANGE_REGEX);
        Matcher matcher = pattern.matcher(ipRange);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String singleIP = "192.168.0.1";
        String ipWithMask = "0.168.255.1/64";
        String ipRange = "192.168.0.1-192.168.0.10";

        System.out.println("Is valid single IP: " + isValidIP(singleIP));
        System.out.println("Is valid IP with CIDR: " + isValidCIDR(ipWithMask));
        System.out.println("Is valid IP range: " + isValidIPRange(ipRange));
    }
}
