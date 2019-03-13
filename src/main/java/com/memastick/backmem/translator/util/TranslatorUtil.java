package com.memastick.backmem.translator.util;

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
}
