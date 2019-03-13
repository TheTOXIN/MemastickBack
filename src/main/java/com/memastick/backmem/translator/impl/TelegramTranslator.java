package com.memastick.backmem.translator.impl;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramTranslator implements Translator {

    private final static Logger log = LoggerFactory.getLogger(TelegramTranslator.class);

    private final static String TEMPLATE = "https://api.telegram.org/bot%s/sendPhoto?chat_id=%s&caption=%s";

    @Value("${api.telegram.bot}")
    private String token;

    @Value("${api.telegram.chat}")
    private String chat;

    private final RestTemplate rest;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public TelegramTranslator(
        RestTemplate rest,
        EvolveMemeService evolveMemeService
    ) {
        this.rest = rest;
        this.evolveMemeService = evolveMemeService;
    }

    @Override
    public void translate(Meme meme) {
        String api = String.format(TEMPLATE, token, chat, prepareText(meme));

        Resource resource = TranslatorUtil.downloadImage(meme.getUrl());
        if (resource == null) return;

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("photo", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> response = rest.exchange(api, HttpMethod.POST, requestEntity, String.class);

        log.info("Translate TELEGRAM meme: " + response.getBody() + " - " + response.getStatusCode());
    }

    private String prepareText(Meme meme) {
        return new StringBuilder()
            .append("МЕМ ДНЯ" + "\n")
            .append("Эволюция №" + evolveMemeService.evolveDay() + "\n")
            .append("Меметик - " + meme.getMemetick().getNick() + "\n")
            .append("Хромосом:" + meme.getChromosomes() + "\n")
            .toString();
    }
}
