package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.evolution.service.EvolveService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslatorDayService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorDayService.class);

    private final List<Translator> translators;
    private final EvolveService evolveService;
    private final MemeRepository memeRepository;
    private final TranslatorDownloader translatorDownloader;
    private final NotifyService notifyService;

    @Value("${memastick.meme.day}")
    private boolean autoMemeDay;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *", zone = "UTC")
    public void autoPublish() {
        if (!autoMemeDay) return;
        long evolution = evolveService.computeEvolution() - 1;

        Meme meme = memeRepository.findSuperMeme(evolution);
        if (meme == null) return;

        publish(meme);
    }

    @Transactional
    public void dayPublish(UUID memeId) {
        Meme meme = memeRepository.findById(memeId).orElse(null);
        if (meme == null) return;

        publish(meme);
    }

    private void publish(Meme meme) {
        log.info("START TRANSLATE PUBLISH");

        File file = translatorDownloader.download(meme);
        if (file == null) return;

        String text = TranslatorUtil.prepareText(meme, meme.getMemetick());
        TranslatorDTO dto = new TranslatorDTO(meme, file, text);

        translators.forEach(t -> t.translate(dto));
        notifyService.sendMEMEDAY(meme);

        log.info("END TRANSLATE PUBLISH");
    }
}
