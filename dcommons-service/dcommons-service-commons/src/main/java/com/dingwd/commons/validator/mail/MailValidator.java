package com.dingwd.commons.validator.mail;

import com.dingwd.commons.exceptions.DParamException;
import com.dingwd.commons.messages.DErrorMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailValidator {

    private static final String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static boolean isMail(String mail) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
}
