package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.translator.component.TranslatorDownloader;
import com.memastick.backmem.translator.dto.TranslatorDTO;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslatorPublishService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorPublishService.class);

    private final List<Translator> translators;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;
    private final TranslatorDownloader translatorDownloader;
    private final NotifyService notifyService;

    @Transactional
    @Scheduled(cron = "0 0 7 * * *", zone = "UTC")
    public void publish() {
        log.info("START TRANSLATE PUBLISH");

        long evolution = evolveMemeService.computeEvolution() - 1;

        Meme meme = memeRepository.findSuperMeme(evolution);
        if (meme == null) return;

        File file = translatorDownloader.download(meme);
        if (file == null) return;

        String text = TranslatorUtil.prepareText(meme, meme.getMemetick());
        TranslatorDTO dto = new TranslatorDTO(meme, file, text);

        translators.forEach(t -> t.translate(dto));
        notifyService.sendMEMEDAY(meme);

        log.info("END TRANSLATE PUBLISH");
    }
}
