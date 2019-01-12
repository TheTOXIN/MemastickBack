package com.memastick.backmem.main.util;

public class ValidationUtil {

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String LOGIN_PATTERN = "((?=.*[A-Za-z0-9])[A-Za-z][A-Za-z0-9]{4,20})";
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,20})";

    public static boolean isEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean validLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    public static boolean checkPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

}
