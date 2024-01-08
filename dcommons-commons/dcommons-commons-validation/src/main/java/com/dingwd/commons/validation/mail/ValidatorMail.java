package com.dingwd.commons.validation.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorMail {

    private static final String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static boolean isMail(String mail) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
}
