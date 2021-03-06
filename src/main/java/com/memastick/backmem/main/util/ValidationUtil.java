package com.memastick.backmem.main.util;

import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.ValidConstant;
import com.memastick.backmem.memes.dto.MemeLohDTO;

import static com.memastick.backmem.main.constant.ValidConstant.*;

public class ValidationUtil {

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String LOGIN_PATTERN = "([A-Za-z0-9]{3,16})";
    private static final String PASSWORD_PATTERN = "^[A-Za-zА-Яа-я\\d@$:._!%*#?&\\-]{4,20}$";

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean checkLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    public static boolean checkPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean checkNick(String nick) {
        return nick.length() >= MIN_LEN_NCK && nick.length() <= MAX_LEN_NCK;
    }

    public static boolean checkPVP(int pvp) {
        return pvp >= BattleConst.MIN_PVP && pvp <= BattleConst.MAX_PVP;
    }

    public static boolean validLoh(MemeLohDTO loh) {
        return (loh.getLol() + loh.getOmg() + loh.getHmm()) == MAX_LOH;
    }

    public static boolean validText(String text) {
        return text != null && !text.equals("") && text.length() <= MAX_TEXT_LEN;
    }
}
