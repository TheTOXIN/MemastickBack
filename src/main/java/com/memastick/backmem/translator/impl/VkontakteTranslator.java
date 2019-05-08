package com.memastick.backmem.translator.impl;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.translator.iface.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VkontakteTranslator implements Translator {

    private final static Logger log = LoggerFactory.getLogger(VkontakteTranslator.class);

    private final RestTemplate rest;

    @Value("${api.vkontakte.key}")
    private String key;

    @Autowired
    public VkontakteTranslator(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public void translate(Meme meme) {

    }
}
