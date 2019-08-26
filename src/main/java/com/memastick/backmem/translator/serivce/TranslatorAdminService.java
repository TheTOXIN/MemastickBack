package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.translator.component.TranslatorDownloader;
import com.memastick.backmem.translator.dto.TranslatorDTO;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TranslatorAdminService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorAdminService.class);

    private final List<Translator> translators;
    private final MemeRepository memeRepository;
    private final TranslatorDownloader translatorDownloader;

    public void adminPublish(UUID memeId) {
        log.info("START TRANSLATE ADMIN");

        Meme meme = memeRepository.tryFindById(memeId);
        File file = translatorDownloader.download(meme);
        String text = TranslatorUtil.prepareAdminText(meme);

        translators.forEach(t -> t.translate(new TranslatorDTO(meme, file, text)));

        log.info("END TRANSLATE ADMIN");
    }
}
