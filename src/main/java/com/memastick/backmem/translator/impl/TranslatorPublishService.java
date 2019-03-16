package com.memastick.backmem.translator.impl;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.translator.iface.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TranslatorPublishService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorPublishService.class);

    private final Translator translator;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;

    @Autowired
    public TranslatorPublishService(
        Translator translator,
        EvolveMemeService evolveMemeService,
        MemeRepository memeRepository
    ) {
        this.translator = translator;
        this.evolveMemeService = evolveMemeService;
        this.memeRepository = memeRepository;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "UTC")
    public void publish() {
        log.info("START TRANSLATE PUBLISH");

        long population = evolveMemeService.evolveDay() - EvolveStep.values().length;

        Meme meme = memeRepository.findSuperMeme(population);
        if (meme == null) return;

        translator.translate(meme);

        log.info("END TRANSLATE PUBLISH");
    }
}
