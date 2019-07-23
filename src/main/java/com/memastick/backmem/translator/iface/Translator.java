package com.memastick.backmem.translator.iface;

import com.memastick.backmem.translator.dto.TranslatorDTO;
import org.springframework.scheduling.annotation.Async;

public interface Translator {

    @Async
    void translate(TranslatorDTO dto);
}
