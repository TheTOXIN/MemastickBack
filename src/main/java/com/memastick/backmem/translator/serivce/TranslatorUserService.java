package com.memastick.backmem.translator.serivce;

import com.memastick.backmem.errors.exception.TranslatorException;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import com.memastick.backmem.translator.component.TranslatorDownloader;
import com.memastick.backmem.translator.dto.TranslatorDTO;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionalException;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TranslatorUserService {

    private final List<Translator> translators;
    private final MemeService memeService;
    private final TranslatorDownloader translatorDownloader;
    private final OauthData oauthData;
    private final MemeCoinService coinService;

    public void userPublish(UUID memeId) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = memeService.findById(memeId);

        if (memetick.equals(meme.getMemetick())) throw new TranslatorException();
        coinService.transaction(memetick, PriceConst.PUBLISH.getValue());

        File file = translatorDownloader.download(meme);
        String text = TranslatorUtil.prepareUserText(meme);

        translators.forEach(t -> t.translate(new TranslatorDTO(meme, file, text)));
    }
}
