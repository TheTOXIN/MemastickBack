package com.memastick.backmem.translator.util;

import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;

public class TranslatorUtil {

    public static String prepareText(Meme meme, Memetick memetick) {
        StringBuilder builder = new StringBuilder();

        builder
            .append("МЕМ ДНЯ ❗️" + "\n")
            .append("\uD83C\uDF0E Эволюция №" + meme.getEvolution() + "\n")
            .append("\uD83D\uDE0E Меметик - " + memetick.getNick() + "\n")
            .append("☘️ Хромосом: " + meme.getChromosomes() + "\n")
            .append(LinkConstant.LINK_MEME + "/" + meme.getId() + "\n");

        if (meme.getText() != null) builder.append(meme.getText());

        return builder.toString();
    }

    public static String prepareAdminText(Meme meme) {
        StringBuilder builder = new StringBuilder();

        builder
            .append("\uD83D\uDC99 МЕМ ОТ АДМИНА" + "\n")
            .append("\uD83E\uDD13 Автор - " + meme.getMemetick().getNick() + "\n")
            .append(LinkConstant.LINK_MEME + "/" + meme.getId() + "\n");

        if (meme.getText() != null) builder.append(meme.getText());

        return builder.toString();
    }

    public static String prepareUserText(Meme meme) {
        StringBuilder builder = new StringBuilder();

        builder
            .append("ПУБЛИКАЦИЯ МЕМЕТИКА\n")
            .append(LinkConstant.LINK_MEME + "/" + meme.getId() + "\n");

        if (meme.getText() != null) builder.append(meme.getText());

        return builder.toString();
    }
}
