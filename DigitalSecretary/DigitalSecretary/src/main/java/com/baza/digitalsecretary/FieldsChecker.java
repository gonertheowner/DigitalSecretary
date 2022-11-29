package com.baza.digitalsecretary;

import java.util.regex.*;

public class FieldsChecker {
    public static String isValidPassword(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.UnicodeBlock.of(password.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return "Пароль содержит кириллицу";
            }
        }
        if (password.length()<6 || password.length()>20) {
            return "Пароль должен содержать от 6 до 20 символов";
        }
        if (password.indexOf(" ")!=-1) {
            return "Пароль содержит пробел";
        }
        String regex = "(\\d[a-z, A-Z]|[a-z, A-Z]\\d)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.find()) {
            return "success";
        }
        else {
            return "the password does not meet the condition";
        }
    }
    public static String isValidLogin (String login) {
        if (login.length() < 1) {
            return "Логин должен содержать хотя бы один символ";
        }
        if (login.indexOf(" ")!=-1) {
            return "Логин содержит пробел";
        }
        if (login.length()>40){
            return "Логин должен содержать меньше 40 символов";
        }
        return "success";
    }
}
