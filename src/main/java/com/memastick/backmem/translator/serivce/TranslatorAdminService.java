package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.translator.component.TranslatorDownloader;
import com.memastick.backmem.translator.dto.TranslatorDTO;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class TranslatorAdminService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorAdminService.class);

    private final List<Translator> translators;
    private final MemeService memeService;
    private final TranslatorDownloader translatorDownloader;

    @Autowired
    public TranslatorAdminService(
        List<Translator> translators,
        MemeService memeService,
        TranslatorDownloader translatorDownloader
    ) {
        this.translators = translators;
        this.memeService = memeService;
        this.translatorDownloader = translatorDownloader;
    }

    public void publish(UUID memeId) {
        log.info("START TRANSLATE ADMIN");

        Meme meme = memeService.findById(memeId);
        File file = translatorDownloader.download(meme);
        String text = TranslatorUtil.prepareAdminText(meme);

        translators.forEach(t -> t.translate(new TranslatorDTO(meme, file, text)));

        log.info("END TRANSLATE ADMIN");
    }
}
