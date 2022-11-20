package com.baza.digitalsecretary;

import java.util.regex.*;

public class FieldsChecker {
    public static String isValidPassword(String password) {
        String regex = "(\\d[a-z, A-Z]|[a-z, A-Z]\\d)";
        String regex1 = "(?=\\S+$)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        Pattern p1 = Pattern.compile(regex1);
        Matcher m1 = p1.matcher(password);
        for (int i = 0; i < password.length(); i++) {
            if (Character.UnicodeBlock.of(password.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return "Consist of cyrillic";
            }
        }
        if (password.length()<6 || password.length()>20) {
            return "password must consist 6-20 symbols";
        }
        if (password.indexOf(" ")!=-1) {
            return "password consist space";
        }
        if (m.find()) {
            return "Success";
        }
        else {
            return "the password does not meet the condition";
        }
    }
}
