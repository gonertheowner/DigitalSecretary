package com.baza.digitalsecretary;

import java.util.regex.*;

public class FieldsChecker {
    public static String isValidPassword(String password) {
        String regex = "(\\d[a-z, A-Z]|[a-z, A-Z]\\d)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
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
    public static String isValidLogin (String login) {
        if (login.indexOf(" ")!=-1) {
            return "login consist space";
        }
        if (login.length()>40){
            return "login must consist less than 40 symbols";
        }
        return "Success";
    }
}
