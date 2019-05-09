package com.memastick.backmem.main.controller;

import com.memastick.backmem.evolution.service.EvolveNextService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.service.MainService;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.impl.VkontakteTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final MainService mainService;
    private final EvolveNextService evolveNextService;
    private final TokenAllowanceSendService allowanceSendService;

    @Autowired
    public MainController(
        MainService mainService,
        EvolveNextService evolveNextService,
        TokenAllowanceSendService allowanceSendService
    ) {
        this.mainService = mainService;
        this.evolveNextService = evolveNextService;
        this.allowanceSendService = allowanceSendService;
    }

    @Autowired
    private List<Translator> translators;

    @Autowired
    private MemeRepository memeRepository;

    @GetMapping("hello")
    public String hello() {
        Meme meme = memeRepository.findSuperMeme(74);
        translators.forEach(t -> t.translate(meme));

        return "Hello, i'm MEMASTICK server!!! ver: " + GlobalConstant.VERSION;
    }

    @GetMapping("next-evolve")
    public void evolve() {
        evolveNextService.evolve();
    }

    @GetMapping("send-allowance")
    public void allowance() {
        allowanceSendService.allowance();
    }

    @GetMapping("home")
    public HomeAPI home() {
        return mainService.home();
    }
}
