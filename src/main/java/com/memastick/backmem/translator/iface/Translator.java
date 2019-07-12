package com.memastick.backmem.translator.iface;

import com.memastick.backmem.memes.entity.Meme;
import org.springframework.scheduling.annotation.Async;

import java.io.File;

public interface Translator {

    @Async
    void translate(File file, Meme meme);

}
