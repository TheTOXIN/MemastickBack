package com.memastick.backmem.translator.util;

import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;

public class TranslatorUtil {

    public static String prepareText(Meme meme) {
        return new StringBuilder()
            .append("МЕМ ДНЯ ❗️" + "\n" )
            .append("\uD83C\uDF0E Эволюция №" + meme.getEvolution() + "\n")
            .append("\uD83D\uDE0E Меметик - " + meme.getMemetick().getNick() + "\n")
            .append("☘️ Хромосом: " + meme.getChromosomes() + "\n")
            .append(GlobalConstant.URL + "/memes/share/" + meme.getId())
            .toString();
    }
}
