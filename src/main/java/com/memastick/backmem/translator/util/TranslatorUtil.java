package com.memastick.backmem.translator.util;

import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TranslatorUtil {

    public static Resource downloadImage(String link) {
        try {
            URL url = new URL(link);
            File file = new File("translator");
            FileUtils.copyURLToFile(url, file);
            return new FileSystemResource(file);
        } catch (IOException ex) {
            return null;
        }
    }

    public static String prepareText(Meme meme) {
        return new StringBuilder()
            .append("МЕМ ДНЯ ❗️" + "\n" )
            .append("\uD83C\uDF0E Эволюция №" + meme.getPopulation() + "\n")
            .append("\uD83D\uDE0E Меметик - " + meme.getMemetick().getNick() + "\n")
            .append("☘️ Хромосом: " + meme.getChromosomes() + "\n")
            .append(GlobalConstant.URL + "/memes/share/" + meme.getId())
            .toString();
    }
}
