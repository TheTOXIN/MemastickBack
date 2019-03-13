package com.memastick.backmem.translator.iface;

import com.memastick.backmem.memes.entity.Meme;
import org.springframework.scheduling.annotation.Async;

public interface Translator {

    @Async
    void translate(Meme meme);

}
