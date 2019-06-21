package com.memastick.backmem.notification.util;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.tokens.constant.TokenType;

public class NotifyUtil {

    public static String typeToStr(Meme meme) {
        MemeType type = meme.getType();
        if (type.equals(MemeType.INDV)) return "ВЫЖИЛ";
        else return "УМЕР";
    }

    public static String tokenToStr(TokenType token) {
        String result = "";

        switch (token) {
            case TUBE: result = "ПРОБИРКА"; break;
            case SCOPE: result = "МИКРОСКОП"; break;
            case MUTAGEN: result = "МУТАГЕН"; break;
            case CROSSOVER: result = "КРОССОВЕР"; break;
            case ANTIBIOTIC: result = "АНТИБИОТИК"; break;
        }

        return result;
    }
}
