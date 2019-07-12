package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.translator.component.TranslatorDownloader;
import com.memastick.backmem.translator.iface.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TranslatorPublishService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorPublishService.class);

    private final List<Translator> translators;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;
    private final TranslatorDownloader translatorDownloader;

    @Autowired
    public TranslatorPublishService(
        List<Translator> translators,
        EvolveMemeService evolveMemeService,
        MemeRepository memeRepository,
        TranslatorDownloader translatorDownloader
    ) {
        this.translators = translators;
        this.evolveMemeService = evolveMemeService;
        this.memeRepository = memeRepository;
        this.translatorDownloader = translatorDownloader;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "UTC")
    public void publish() {
        log.info("START TRANSLATE PUBLISH");

        long evolution = evolveMemeService.computeEvolution() - 1;

        Meme meme = memeRepository.findSuperMeme(evolution);
        if (meme == null) return;

        File file = translatorDownloader.download(meme);
        if (file == null) return;

        translators.forEach(t -> t.translate(file, meme));

        log.info("END TRANSLATE PUBLISH");
    }
}
